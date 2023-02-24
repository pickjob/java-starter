package framework.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoChannelHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LogManager.getLogger(EchoChannelHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf)msg;
        in.markReaderIndex();
        while (in.isReadable()) {
            logger.info(in.readByte());
        }
        in.resetReaderIndex();
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }
}
