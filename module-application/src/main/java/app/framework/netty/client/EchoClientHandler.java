package app.framework.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EchoClientHandler: Echo Client端实现
 *
 * @author: pickjob@126.com
 * @date: 2024-09-22
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();
    private static final Integer SIZE = 255;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf firstMessage = Unpooled.buffer(SIZE);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }
        ctx.writeAndFlush(firstMessage);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause);
        ctx.close();
    }
}