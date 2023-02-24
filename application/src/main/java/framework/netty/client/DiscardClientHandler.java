package framework.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles a client-side channel.
 */
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger loggger = LogManager.getLogger(DiscardClientHandler.class);
    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        int size = 256;
        // Initialize the message.
        content = ctx.alloc()
                     .directBuffer(size)
                     .writeZero(size);
        // Send the initial messages.
        generateTraffic();
    }

    private void generateTraffic() {
        // Flush the outbound buffer to the basic.socket.
        // Once flushed, generate the same amount of traffic again.
        ctx.writeAndFlush(content.retainedDuplicate())
           .addListener(trafficGenerator);
    }

    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            if (future.isSuccess()) {
                generateTraffic();
            } else {
                loggger.error(future.cause().getMessage(), future.cause());
                future.channel().close();
            }
        }
    };

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Do nothing
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        content.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        loggger.error(cause.getMessage(), cause);
        ctx.close();
    }
}
