package showcase.smpp.pdu.body;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.BodyPdu;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class NOOPBody extends BodyPdu {
    private static Logger logger = LogManager.getLogger();

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void encoding(ByteBuf buf) {
        return;
    }

    @Override
    public void decoding(ByteBuf buf) {
        return;
    }

    @Override
    public String toString() {
        return "NOOPBody{}";
    }
}
