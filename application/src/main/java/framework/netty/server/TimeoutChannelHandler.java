package framework.netty.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeoutChannelHandler extends ChannelDuplexHandler {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                logger.info("will close channel");
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                logger.info("will close channel");
                ctx.close();
            }
        }
    }
}
 