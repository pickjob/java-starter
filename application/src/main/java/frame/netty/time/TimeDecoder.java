package frame.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LogManager.getLogger(TimeDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if ( in.readableBytes() < 4) return;
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
