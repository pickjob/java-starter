package showcase.smpp.pdu;

import io.netty.buffer.ByteBuf;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public interface Stream {
    void encoding(ByteBuf buf);

    void decoding(ByteBuf buf);
}
