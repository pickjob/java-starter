package frame.netty;

import app.common.IShowCase;
import frame.netty.discard.DiscardClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClientShowCase implements IShowCase {

    @Override
    public void showSomething() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p
                            // discard
                            .addLast(new DiscardClientHandler())
                            // echo
//                            .addLast(new EchoClientHandler())
                            // time
//                            .addLast(new TimeDecoder(), new TimeClientHandler())
                            // http Hello World
//                            .addLast(new HttpClientCodec(), new MyHttpClientHandler())
                            ;
                        }
                    });

            // Make the connection attempt.
            ChannelFuture f = b.connect("localhost", 8080).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
//
//    @Override
//    public int order() {
//        return 1;
//    }
}
