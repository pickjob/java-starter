package framework.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import framework.netty.discard.DiscardClientHandler;

public class NettyClientShowCase {

    public static void main(String[] argv){
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
}
