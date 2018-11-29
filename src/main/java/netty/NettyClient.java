package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.discard.DiscardClientHandler;
import netty.echo.EchoClientHandler;
import netty.time.TimeClientHandler;
import netty.time.TimeDecoder;
import netty.time.TimeServerHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CommonKey;

public class NettyClient {
    private static Logger logger = LogManager.getLogger(NettyClient.class);

    public static void main(String[] argv){
        new NettyClient().run(CommonKey.HOST, CommonKey.PORT);
    }

    private void run(String host, int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p
                     //discard
                     .addLast(new DiscardClientHandler())
                     //echo
//                     .addLast(new EchoClientHandler())
                     //time
//                     .addLast(new TimeDecoder(), new TimeClientHandler())
                     ;
                 }
             });

            // Make the connection attempt.
            ChannelFuture f = b.connect(host, port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch(Exception e) {
            logger.error(e.getMessage(), e.getCause());
        } finally {
            group.shutdownGracefully();
        }
    }
}
