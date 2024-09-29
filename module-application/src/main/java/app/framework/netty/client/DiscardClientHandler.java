package app.framework.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DiscardClientHandler: Discard Client端实现
 *
 * @author: pickjob@126.com
 * @date: 2024-09-04
 */
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();
    private static final Integer DISCARD_SIZE = 256;
    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        content = ctx.alloc().directBuffer(DISCARD_SIZE).writeZero(DISCARD_SIZE);
        ctx.writeAndFlush(content.retainedDuplicate());
        ctx.flush();
        content.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause);
        ctx.close();
    }
}