package smpp.pdu.body;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.pdu.BodyPdu;
import smpp.pdu.Stream;
import smpp.util.CharsetEnum;
import smpp.util.DeliveryReceiptStatesEnum;
import smpp.util.EsmClassEnum;
import smpp.util.StringCodeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

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
            buf.writeByte(bytes.length);
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
            }
            shortMessage = charsetEnum.decodingString(buf, smLength);
            if ((esmClass & EsmClassEnum.DeliveryReceipt.getEsmClass()) == EsmClassEnum.DeliveryReceipt.getEsmClass()) {
                receipt = new Receipt();
                receipt.decoding(shortMessage);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void finalDescide() {
        if (receipt != null) { // Delivery Receipt
            shortMessage = receipt.toString();
            esmClassEnum = EsmClassEnum.DeliveryReceipt;
        }
        if (esmClassEnum != null) {
            esmClass = esmClassEnum.getEsmClass();
        }
        if (charsetEnum == null) charsetEnum = CharsetEnum.Latin1;
        if (!isSetDCS && charsetEnum != null) {
            dataCoding = charsetEnum.getDataCodingSchema();
        }
    }

    private class UDHI implements Stream {
        private byte length = 5;   // Length of UDH (5 bytes)
        private byte indicator = 0;  // Indicator for concatenated message
        private byte subheader = 3;  // Subheader Length (3 bytes)
        private byte serial = 0;  // message identification
        private byte totalNum = 0; // Number of pieces of the concatenated message
        private byte index = 0;  // Sequence number

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

        public byte getIndicator() {
            return indicator;
        }

        public void setIndicator(byte indicator) {
            this.indicator = indicator;
        }

        public byte getSubheader() {
            return subheader;
        }

        public void setSubheader(byte subheader) {
            this.subheader = subheader;
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
        private static final String report = "id:%s sub:%03d dlvrd:%03d submit date:%s done date:%s stat:%s err:%03d text:%s";
        private String messageId;
        private Integer totalCt;
        private Integer sucCt;
        private Date submitDate;
        private Date doneDate;
        private Integer error = 0;
        private String text;

        private DeliveryReceiptStatesEnum deliveryReceiptStatesEnum;

        private SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmm");

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

        @Override
        public String toString() {
            return String.format(report, messageId, totalCt, sucCt,
                    sdf.format(submitDate), sdf.format(doneDate), deliveryReceiptStatesEnum.toString(), error, text);
        }

        public void decoding(String content) {
            // TODO: 反解希Receipt
            logger.info("original Text:{}", content);
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

        public Integer getError() {
            return error;
        }

        public void setError(Integer error) {
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

        public SimpleDateFormat getSdf() {
            return sdf;
        }

        public void setSdf(SimpleDateFormat sdf) {
            this.sdf = sdf;
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

}
