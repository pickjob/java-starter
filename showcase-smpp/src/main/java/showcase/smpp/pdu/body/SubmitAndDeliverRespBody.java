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
public class SubmitAndDeliverRespBody extends BodyPdu {
    private static final Logger logger = LogManager.getLogger();
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public int getSize() {
        int length = 0;
        try {
            byte[] bytes = StringCodeUtil.encodingCString(messageId);
            length = bytes.length;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return length;
    }

    @Override
    public void encoding(ByteBuf buf) {
        try {
            byte[] bytes = StringCodeUtil.encodingCString(messageId);
            buf.writeBytes(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void decoding(ByteBuf buf) {
        try {
            messageId = StringCodeUtil.decodingCString(buf);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "SubmitAndDeliverRespBody{" +
                "messageId='" + messageId + '\'' +
                '}';
    }
}
