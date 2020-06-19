package frame.netty;

import frame.netty.discard.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServerShowCase {
    static final String REMOTE_HOST = System.getProperty("remoteHost", "www.google.com");
    static final int REMOTE_PORT = Integer.parseInt(System.getProperty("remotePort", "443"));

    public static void main(String[] argv) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p
                            // discard
                             .addLast(new DiscardServerHandler())
                            // echo
//                            .addLast(new EchoServerHandler())
                            // time
//                            .addLast(new TimeEncoder(), new TimeServerHandler())
                            // http hello world
//                            .addLast(new HttpServerCodec(), new HttpHelloWorldServerHandler())
                             ;
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(8080).sync();

            // Wait until the server basic.socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
