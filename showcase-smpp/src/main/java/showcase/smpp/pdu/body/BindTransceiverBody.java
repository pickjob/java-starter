package showcase.smpp.pdu.body;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.BodyPdu;
import showcase.smpp.util.StringCodeUtil;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class BindTransceiverBody extends BodyPdu {
    private static final Logger logger = LogManager.getLogger();
    private String systemId;
    private String password;
    private String systemType;
    private byte interfaceVersion;
    private byte addrTon;
    private byte addrNpi;
    private String addressRange;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public byte getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(byte interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public byte getAddrTon() {
        return addrTon;
    }

    public void setAddrTon(byte addrTon) {
        this.addrTon = addrTon;
    }

    public byte getAddrNpi() {
        return addrNpi;
    }

    public void setAddrNpi(byte addrNpi) {
        this.addrNpi = addrNpi;
    }

    public String getAddressRange() {
        return addressRange;
    }

    public void setAddressRange(String addressRange) {
        this.addressRange = addressRange;
    }

    @Override
    public int getSize() {
        int length = 0;
        try {
            byte[] bytes = StringCodeUtil.encodingCString(systemId);
            length += bytes.length;
            bytes = StringCodeUtil.encodingCString(password);
            length += bytes.length;
            bytes = StringCodeUtil.encodingCString(systemType);
            length += bytes.length;
            length += 3;
            bytes = StringCodeUtil.encodingCString(addressRange);
            length += bytes.length;
        } catch (Exception e) {
            logger.error(e, e);
        }
        return length;
    }

    @Override
    public void encoding(ByteBuf buf) {
        try {
            byte[] bytes = StringCodeUtil.encodingCString(systemId);
            buf.writeBytes(bytes);
            bytes = StringCodeUtil.encodingCString(password);
            buf.writeBytes(bytes);
            bytes = StringCodeUtil.encodingCString(systemType);
            buf.writeBytes(bytes);
            buf.writeByte(interfaceVersion);
            buf.writeByte(addrTon);
            buf.writeByte(addrNpi);
            bytes = StringCodeUtil.encodingCString(addressRange);
            buf.writeBytes(bytes);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    @Override
    public void decoding(ByteBuf buf) {
        try {
            systemId = StringCodeUtil.decodingCString(buf);
            password = StringCodeUtil.decodingCString(buf);
            systemType = StringCodeUtil.decodingCString(buf);
            interfaceVersion = buf.readByte();
            addrTon = buf.readByte();
            addrNpi = buf.readByte();
            addressRange = StringCodeUtil.decodingCString(buf);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    @Override
    public String toString() {
        return "BindTransceiverBody{" +
                "systemId='" + systemId + '\'' +
                ", password='" + password + '\'' +
                ", systemType='" + systemType + '\'' +
                ", interfaceVersion=" + interfaceVersion +
                ", addrTon=" + addrTon +
                ", addrNpi=" + addrNpi +
                ", addressRange='" + addressRange + '\'' +
                '}';
    }
}
