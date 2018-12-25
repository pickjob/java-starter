package smpp.pdu;

import io.netty.buffer.ByteBuf;

public interface Stream {
    void encoding(ByteBuf buf);

    void decoding(ByteBuf buf);
}
