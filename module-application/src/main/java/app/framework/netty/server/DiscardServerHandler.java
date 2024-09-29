package app.framework.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DiscardServerHandler: Discard Server端实现
 *
 * @author pickjob@126.com
 * @date 2024-09-15
 **/
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 释放 Reference Count
        // ((ByteBuf) msg).release();
        // try {
        //     // Do something with msg
        // } finally {
        //     ReferenceCountUtil.release(msg);
        // }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause);
        ctx.close();
    }
}