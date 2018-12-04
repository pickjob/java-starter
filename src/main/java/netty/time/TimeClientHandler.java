package netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class TimeClientHandler extends SimpleChannelInboundHandler<UnixTime> {
    private Logger logger = LogManager.getLogger(TimeClientHandler.class);
    private ByteBuf buf;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UnixTime msg) throws Exception {
        logger.info(msg);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        buf = ctx.alloc()
                 .buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        buf.release();
        buf = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }
}
