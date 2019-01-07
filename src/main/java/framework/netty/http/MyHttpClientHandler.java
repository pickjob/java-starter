package netty.http;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyHttpClientHandler extends ChannelDuplexHandler {
    private static Logger logger = LogManager.getLogger("client");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel active");
        HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(msg.getClass());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }
}
