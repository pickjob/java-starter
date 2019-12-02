package framework.smpp.pdu.body;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.BodyPdu;
import framework.smpp.util.StringCodeUtil;

public class BindTransceiverRespBody extends BodyPdu {
    private static final Logger logger = LogManager.getLogger(BindTransceiverRespBody.class);
    private String systemId;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public int getSize() {
        int length = 0;
        try {
            byte[] bytes = StringCodeUtil.encodingCString(systemId);
            length += bytes.length;
        } catch (Exception e) {
            logger.error(e, e);
        }
        return length;
    }

    @Override
    public void encoding(ByteBuf buf) {
        int length = 0;
        try {
            byte[] bytes = StringCodeUtil.encodingCString(systemId);
            buf.writeBytes(bytes);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    @Override
    public void decoding(ByteBuf buf) {
        try {
            systemId = StringCodeUtil.decodingCString(buf);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    @Override
    public String toString() {
        return "BindTransceiverRespBody{" +
                "systemId='" + systemId + '\'' +
                '}';
    }
}
