package showcase.smpp.pdu.body;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.BodyPdu;
import showcase.smpp.pdu.entity.TLV;
import showcase.smpp.util.CharsetEnum;
import showcase.smpp.util.DeliveryReceiptStatesEnum;
import showcase.smpp.util.EsmClassEnum;
import showcase.smpp.util.StringCodeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class SubmitAndDeliverBody extends BodyPdu {
    private static final Logger logger = LogManager.getLogger(SubmitAndDeliverBody.class);
    private String serviceType;
    private byte sourceAddrTon;
    private byte sourceAddrNpi;
    private String sourceAddr;
    private byte destAddrTon;
    private byte destAddrNpi;
    private String destinationAddr;
    private byte esmClass;
    private byte protocolId;
    private byte priorityFlag;
    private String scheduleDeliveryTime;
    private String validityPeriod;
    private byte registeredDelivery;
    private byte replaceIfPresentFlag;
    private byte dataCoding;
    private byte smDefaultMsgId;
    private byte smLength;
    private String shortMessage;
    private List<TLV> tlvs;
    // 配套类
    private CharsetEnum charsetEnum;
    private EsmClassEnum esmClassEnum;
    private UDHI udhi;
    private Receipt receipt;
    private boolean isSetDCS = false;

    @Override
    public int getSize() {
        finalDescide();
        int length = 0;
        try {
            byte[] bytes = StringCodeUtil.encodingCString(serviceType);
            length += bytes.length;
            length += 2;
            bytes = StringCodeUtil.encodingCString(sourceAddr);
            length += bytes.length;
            length += 2;
            bytes = StringCodeUtil.encodingCString(destinationAddr);
            length += bytes.length;
            length += 3;
            bytes = StringCodeUtil.encodingCString(scheduleDeliveryTime);
            length += bytes.length;
            bytes = StringCodeUtil.encodingCString(validityPeriod);
            length += bytes.length;
            length += 5;
            if (udhi != null) length += udhi.getSize();
            bytes = charsetEnum.encodingString(shortMessage);
            length += bytes.length;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return length;
    }

    @Override
    public void encoding(ByteBuf buf) {
        try {
            byte[] bytes = StringCodeUtil.encodingCString(serviceType);
            buf.writeBytes(bytes);
            buf.writeByte(sourceAddrTon);
            buf.writeByte(sourceAddrNpi);
            bytes = StringCodeUtil.encodingCString(sourceAddr);
            buf.writeBytes(bytes);
            buf.writeByte(destAddrTon);
            buf.writeByte(destAddrNpi);
            bytes = StringCodeUtil.encodingCString(destinationAddr);
            buf.writeBytes(bytes);
            buf.writeByte(esmClass);
            buf.writeByte(protocolId);
            buf.writeByte(priorityFlag);
            bytes = StringCodeUtil.encodingCString(scheduleDeliveryTime);
            buf.writeBytes(bytes);
            bytes = StringCodeUtil.encodingCString(validityPeriod);
            buf.writeBytes(bytes);
            buf.writeByte(registeredDelivery);
            buf.writeByte(replaceIfPresentFlag);
            buf.writeByte(dataCoding);
            buf.writeByte(smDefaultMsgId);
            bytes = charsetEnum.encodingString(shortMessage);
            if(udhi != null) {
                buf.writeByte(udhi.getSize() + bytes.length);
                udhi.encoding(buf);
            } else {
                buf.writeByte(bytes.length);
            }
            buf.writeBytes(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void decoding(ByteBuf buf) {
        try {
            serviceType = StringCodeUtil.decodingCString(buf);
            sourceAddrTon = buf.readByte();
            sourceAddrNpi = buf.readByte();
            sourceAddr = StringCodeUtil.decodingCString(buf);
            destAddrTon = buf.readByte();
            destAddrNpi = buf.readByte();
            destinationAddr = StringCodeUtil.decodingCString(buf);
            esmClass = buf.readByte();
            protocolId = buf.readByte();
            priorityFlag = buf.readByte();
            scheduleDeliveryTime = StringCodeUtil.decodingCString(buf);
            validityPeriod = StringCodeUtil.decodingCString(buf);
            registeredDelivery = buf.readByte();
            replaceIfPresentFlag = buf.readByte();
            dataCoding = buf.readByte();
            charsetEnum = CharsetEnum.DEFAULT.chooseCharsetEnum(dataCoding);
            smDefaultMsgId = buf.readByte();
            smLength = buf.readByte();
            if ((esmClass & EsmClassEnum.ConcatenatedMessages.getEsmClass()) == EsmClassEnum.ConcatenatedMessages.getEsmClass()) {
                udhi = new UDHI();
                udhi.decoding(buf);
                shortMessage = charsetEnum.decodingString(buf, (smLength & 0xFF) - udhi.getSize());
            } else {
                shortMessage = charsetEnum.decodingString(buf, smLength & 0xFF);
            }
            if ((esmClass & EsmClassEnum.DeliveryReceipt.getEsmClass()) == EsmClassEnum.DeliveryReceipt.getEsmClass()) {
                receipt = Receipt.decoding(shortMessage);
            }
            tlvs = TLV.readTlvs(buf);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void finalDescide() {
        if (receipt != null) { // Delivery Receipt
            shortMessage = receipt.format();
            esmClassEnum = EsmClassEnum.DeliveryReceipt;
        }
        if (udhi != null) {
            esmClassEnum = EsmClassEnum.ConcatenatedMessages;
        }
        if (esmClassEnum != null) {
            esmClass = esmClassEnum.getEsmClass();
        }
        if (charsetEnum == null) charsetEnum = CharsetEnum.Latin1;
        if (!isSetDCS && charsetEnum != null) {
            dataCoding = charsetEnum.getDataCodingSchema();
        }
    }

    public static class UDHI extends BodyPdu {
        private byte length = 5;   // Length of UDH (5 bytes)
        private byte indicator = 0;  // Indicator for concatenated message
        private byte subheader = 3;  // Subheader Length (3 bytes)
        private byte serial = 0;  // message identification
        private byte totalNum = 0; // Number of pieces of the concatenated message
        private byte index = 0;  // Sequence number

        @Override
        public int getSize() {
            return 6;
        }

        @Override
        public void encoding(ByteBuf buf) {
            buf.writeByte(length);
            buf.writeByte(indicator);
            buf.writeByte(subheader);
            buf.writeByte(serial);
            buf.writeByte(totalNum);
            buf.writeByte(index);
        }

        @Override
        public void decoding(ByteBuf buf) {
            length = buf.readByte();
            indicator = buf.readByte();
            subheader = buf.readByte();
            serial = buf.readByte();
            totalNum = buf.readByte();
            index = buf.readByte();
        }

        public byte getLength() {
            return length;
        }

        public void setLength(byte length) {
            this.length = length;
        }

        public byte getSerial() {
            return serial;
        }

        public void setSerial(byte serial) {
            this.serial = serial;
        }

        public byte getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(byte totalNum) {
            this.totalNum = totalNum;
        }

        public byte getIndex() {
            return index;
        }

        public void setIndex(byte index) {
            this.index = index;
        }
    }

    public static class Receipt{
        // id:0000000012345678 sub:001 dlvrd:001 submit date:1601010100 done date:1601010101 stat:DELIVRD err:000 text:none
        private static final String report = "id:%s sub:%03d dlvrd:%03d submit date:%s done date:%s stat:%s err:%s text:%s";
        private String messageId;
        private Integer totalCt;
        private Integer sucCt;
        private Date submitDate;
        private Date doneDate;
        private String stat;
        private String error;
        private String text;

        private DeliveryReceiptStatesEnum deliveryReceiptStatesEnum;

        private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmm");

        public static Receipt success(String messageId, DeliveryReceiptStatesEnum deliveryReceiptStatesEnum) {
            Receipt receipt = new Receipt();
            receipt.setMessageId(messageId);
            receipt.setTotalCt(1);
            receipt.setSucCt(1);
            receipt.setSubmitDate(new Date());
            receipt.setDoneDate(new Date());
            receipt.setDeliveryReceiptStatesEnum(deliveryReceiptStatesEnum);
            return receipt;
        }

        public String format() {
            if (StringUtils.isBlank(error)) error = "000";
            return String.format(report, messageId, totalCt, sucCt,
                    sdf.format(submitDate), sdf.format(doneDate), deliveryReceiptStatesEnum.toString(), error, text);
        }

        public static Receipt decoding(String content) {
            Receipt receipt = new Receipt();
            receipt.setMessageId(content.substring(0, content.indexOf("sub")).substring(3).trim());
            receipt.setTotalCt(Integer.valueOf(content.substring(content.indexOf("sub"), content.indexOf("dlvrd")).substring(4).trim()));
            receipt.setSucCt(Integer.valueOf(content.substring(content.indexOf("dlvrd"), content.indexOf("submit date")).substring(6).trim()));
            try {
                receipt.setSubmitDate(sdf.parse(content.substring(content.indexOf("submit date"), content.indexOf("done date")).substring(12).trim()));
                receipt.setDoneDate(sdf.parse(content.substring(content.indexOf("done date"), content.indexOf("stat")).substring(10).trim()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            receipt.setStat(content.substring(content.indexOf("stat"), content.indexOf("err")).substring(5).trim());
            receipt.setError(content.substring(content.indexOf("err"), content.indexOf("text")).substring(4).trim());
            receipt.setText(content.substring(content.indexOf("text")).substring(5).trim());
            logger.info("original Text:{}, decode receipt: {}", content, receipt);
            return receipt;
        }

        public static String getReport() {
            return report;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public Integer getTotalCt() {
            return totalCt;
        }

        public void setTotalCt(Integer totalCt) {
            this.totalCt = totalCt;
        }

        public Integer getSucCt() {
            return sucCt;
        }

        public void setSucCt(Integer sucCt) {
            this.sucCt = sucCt;
        }

        public Date getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(Date submitDate) {
            this.submitDate = submitDate;
        }

        public Date getDoneDate() {
            return doneDate;
        }

        public void setDoneDate(Date doneDate) {
            this.doneDate = doneDate;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public DeliveryReceiptStatesEnum getDeliveryReceiptStatesEnum() {
            return deliveryReceiptStatesEnum;
        }

        public void setDeliveryReceiptStatesEnum(DeliveryReceiptStatesEnum deliveryReceiptStatesEnum) {
            this.deliveryReceiptStatesEnum = deliveryReceiptStatesEnum;
        }

        @Override
        public String toString() {
            return "Receipt{" +
                    "messageId='" + messageId + '\'' +
                    ", totalCt=" + totalCt +
                    ", sucCt=" + sucCt +
                    ", submitDate=" + submitDate +
                    ", doneDate=" + doneDate +
                    ", stat='" + stat + '\'' +
                    ", error=" + error +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public byte getSourceAddrTon() {
        return sourceAddrTon;
    }

    public void setSourceAddrTon(byte sourceAddrTon) {
        this.sourceAddrTon = sourceAddrTon;
    }

    public byte getSourceAddrNpi() {
        return sourceAddrNpi;
    }

    public void setSourceAddrNpi(byte sourceAddrNpi) {
        this.sourceAddrNpi = sourceAddrNpi;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    public byte getDestAddrTon() {
        return destAddrTon;
    }

    public void setDestAddrTon(byte destAddrTon) {
        this.destAddrTon = destAddrTon;
    }

    public byte getDestAddrNpi() {
        return destAddrNpi;
    }

    public void setDestAddrNpi(byte destAddrNpi) {
        this.destAddrNpi = destAddrNpi;
    }

    public String getDestinationAddr() {
        return destinationAddr;
    }

    public void setDestinationAddr(String destinationAddr) {
        this.destinationAddr = destinationAddr;
    }

    public byte getEsmClass() {
        return esmClass;
    }

    public void setEsmClass(byte esmClass) {
        this.esmClass = esmClass;
    }

    public byte getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(byte protocolId) {
        this.protocolId = protocolId;
    }

    public byte getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(byte priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public String getScheduleDeliveryTime() {
        return scheduleDeliveryTime;
    }

    public void setScheduleDeliveryTime(String scheduleDeliveryTime) {
        this.scheduleDeliveryTime = scheduleDeliveryTime;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public byte getRegisteredDelivery() {
        return registeredDelivery;
    }

    public void setRegisteredDelivery(byte registeredDelivery) {
        this.registeredDelivery = registeredDelivery;
    }

    public byte getReplaceIfPresentFlag() {
        return replaceIfPresentFlag;
    }

    public void setReplaceIfPresentFlag(byte replaceIfPresentFlag) {
        this.replaceIfPresentFlag = replaceIfPresentFlag;
    }

    public byte getDataCoding() {
        return dataCoding;
    }

    public void setDataCoding(byte dataCoding) {
        this.dataCoding = dataCoding;
        this.isSetDCS = true;
    }

    public byte getSmDefaultMsgId() {
        return smDefaultMsgId;
    }

    public void setSmDefaultMsgId(byte smDefaultMsgId) {
        this.smDefaultMsgId = smDefaultMsgId;
    }

    public byte getSmLength() {
        return smLength;
    }

    public void setSmLength(byte smLength) {
        this.smLength = smLength;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    public CharsetEnum getCharsetEnum() {
        return charsetEnum;
    }

    public void setCharsetEnum(CharsetEnum charsetEnum) {
        this.charsetEnum = charsetEnum;
    }

    public EsmClassEnum getEsmClassEnum() {
        return esmClassEnum;
    }

    public void setEsmClassEnum(EsmClassEnum esmClassEnum) {
        this.esmClassEnum = esmClassEnum;
    }

    public UDHI getUdhi() {
        return udhi;
    }

    public void setUdhi(UDHI udhi) {
        this.udhi = udhi;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public List<TLV> getTlvs() {
        return tlvs;
    }

    public void setTlvs(List<TLV> tlvs) {
        this.tlvs = tlvs;
    }
}
