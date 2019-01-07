package framework.netty.time;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(TimeServerHandler.class);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new UnixTime());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }
}
