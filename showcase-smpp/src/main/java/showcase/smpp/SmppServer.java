package showcase.smpp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.handler.SmppHandler;
import showcase.smpp.handler.StreamDecoder;
import showcase.smpp.handler.StreamEncoder;
import showcase.smpp.util.SMPPConfig;

import java.util.concurrent.TimeUnit;

public class SmppServer {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        SMPPConfig.Server config = SMPPConfig.getServerConfig();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                    new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, -4, 0)
                                    , new StreamEncoder()
                                    , new StreamDecoder()
                                    , new IdleStateHandler(100, 100, 100, TimeUnit.SECONDS)
                                    , new SmppHandler()
                            );
                        }
                    });
            ChannelFuture f = b.bind(config.getPort()).sync();
            logger.info("SMPP Server 启动成功, 等待连接 ... ");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
