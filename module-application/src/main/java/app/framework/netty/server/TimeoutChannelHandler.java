package app.framework.netty.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TimeoutChannelHandler: 超时事件处理
 *
 *  @author: pickjob@126.com
 *  @date: 2024-09-04
 */
public class TimeoutChannelHandler extends ChannelDuplexHandler {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent e) {
            if (e.state() == IdleState.READER_IDLE || e.state() == IdleState.WRITER_IDLE) {
                logger.info("will close channel");
                ctx.close();
            }
        }
    }
}