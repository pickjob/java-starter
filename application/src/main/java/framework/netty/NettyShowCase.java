package framework.netty;

import framework.netty.client.DiscardClientHandler;
import framework.netty.server.DiscardChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/***
 * Netty: NIO框架
 *      io.netty.channel.ChannelPipeline(责任链模式): ChannelHandler 流水线 处理Socket 读取、写入
 *          io.netty.channel.ChannelHandler
 *              申明方法:
 *                  void handlerAdded(ChannelHandlerContext ctx) throws Exception;
 *                  void handlerRemoved(ChannelHandlerContext ctx) throws Exception;
 *              子接口: 按流向区分
 *                  io.netty.channel.ChannelInboundHandler
 *                      // 原始流水线 读操作
 *                      void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception;
 *                      void channelReadComplete(ChannelHandlerContext ctx) throws Exception;
 *                      // 自定义事件
 *                      void userEventTriggered(ChannelHandlerContext ctx,Object evt) throws Exception;
 *                      // 状态变化
 *                      void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception;
 *                      // 活动
 *                      void channelActive(ChannelHandlerContext ctx) throws Exception;
 *                      void channelInactive(ChannelHandlerContext ctx) throws Exception;
 *                      // 注册
 *                      void channelRegistered(ChannelHandlerContext ctx) throws Exception;
 *                      void channelUnregistered(ChannelHandlerContext ctx) throws Exception;
 *                  io.netty.channel.ChannelOutboundHandler
 *                      // 原始流水线 写操作
 *                      void write(ChannelHandlerContext ctx,Object msg,ChannelPromise promise) throws Exception;
 *                      void flush(ChannelHandlerContext ctx) throws Exception;
 *                      void read(ChannelHandlerContext ctx) throws Exception;
 *                      // Socket 事件
 *                      void bind(ChannelHandlerContext ctx,SocketAddress localAddress,ChannelPromise promise) throws Exception;
 *                      void connect(ChannelHandlerContext ctx,SocketAddress remoteAddress, SocketAddress localAddress,ChannelPromise promise) throws Exception
 *                      void disconnect(ChannelHandlerContext ctx,ChannelPromise promise) throws Exception;
 *                      void close(ChannelHandlerContext ctx,ChannelPromise promise) throws Exception;
 *                      void deregister(ChannelHandlerContext ctx,ChannelPromise promise) throws Exception;
 *              主要实现类: 实现ChannelInboundHandler、ChannelOutboundHandler
 *                  io.netty.channel.ChannelInboundHandlerAdapter: 流入
 *                      io.netty.channel.SimpleChannelInboundHandler<I>: 处理字符消息 自动释放入口字符串
 *                      io.netty.handler.codec.MessageToMessageDecoder<I>: 消息转换消息, 自动释放入口消息
 *                      io.netty.channel.SimpleUserEventChannelHandler<I>: 处理用户自定义该消息
 *                  io.netty.channel.ChannelOutboundHandlerAdapter: 流出
 *                  io.netty.channel.ChannelDuplexHandler: 双向实现
 *                  io.netty.channel.ChannelInitializer: 向 Channel 注册 ChannelHandler PipeLine
 *              常用ChannelHandler:
 *                  解码器:
 *                      io.netty.handler.codec.ByteToMessageDecoder: ChannelInboundHandlerAdapter: 解码器
 *                          io.netty.handler.codec.LineBasedFrameDecoder: '\n', '\r\n' 数据帧分割
 *                          io.netty.handler.codec.DelimiterBasedFrameDecoder: 指定分隔符  数据帧分割
 *                          io.netty.handler.codec.FixedLengthFrameDecoder: 固定长度  数据帧分割
 *                          io.netty.handler.codec.LengthFieldBasedFrameDecoder: 指定长度 数据帧分割
 *                  编码器:
 *                      io.netty.handler.codec.MessageToByteEncoder<I>
 *                  消息转换器:
 *                      io.netty.handler.codec.MessageToMessageDecoder<I>
 *                      io.netty.handler.codec.MessageToMessageEncoder<I>
 *                      io.netty.handler.codec.MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN>
 *                  心跳检测ChannelHandler:
 *                      io.netty.handler.timeout.IdleStateHandler: 触发自定义事件 IdleStateEvent
 *                          io.netty.handler.timeout.ReadTimeoutHandler: 触发异常  ReadTimeoutException
 *                          io.netty.handler.timeout.WriteTimeoutHandler: 触发异常 WriteTimeoutException
 *                  流量控制:
 *                      io.netty.handler.flow.FlowControlHandler: 一次只产生一个对象给下一个ChannelHandler
 *      java -Dio.netty.leakDetection.level=paranoid; : 泄露检测
 *      ChannelOption
 *          Netty配置
 *              ChannelOption.CONNECT_TIMEOUT_MILLIS : 连接超时毫秒数，默认值30000毫秒
 *              ChannelOption.MAX_MESSAGES_PER_READ : 一次Loop读取的最大消息数，对于ServerChannel或者NioByteChannel，默认值为16，其他Channel默认值为1。
 *              ChannelOption.WRITE_SPIN_COUNT : 一个Loop写操作执行的最大次数，默认值为16
 *              ChannelOption.ALLOCATOR : ByteBuf的分配器，默认值为ByteBufAllocator.DEFAULT，4.0版本为UnpooledByteBufAllocator，4.1版本为PooledByteBufAllocator
 *              ChannelOption.RCVBUF_ALLOCATOR : 用于Channel分配接受Buffer的分配器
 *              ChannelOption.AUTO_READ : 自动读取，默认值为True
 *              ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK : 写高水位标记，默认值64KB
 *              ChannelOption.WRITE_BUFFER_LOW_WATER_MARK : 写低水位标记，默认值32KB
 *              ChannelOption.MESSAGE_SIZE_ESTIMATOR : 消息大小估算器，默认为DefaultMessageSizeEstimator.DEFAULT
 *              ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP: 单线程执行ChannelPipeline中的事件，默认值为True
 *          Socket配置
 *              ChannelOption.SO_BACKLOG : 初始化服务端可连接队列
 *              ChannelOption.SO_REUSEADDR : 允许重复使用本地地址和端口
 *              ChannelOption.SO_KEEPALIVE : 当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接
 *              ChannelOption.SO_SNDBUF和ChannelOption.SO_RCVBUF : 发送缓冲区大小和接受缓冲区大小
 *              ChannelOption.SO_LINGER : 阻塞close()的调用时间，直到数据完全发送
 *              ChannelOption.TCP_NODELAY : 小数据即时传输
 *              ChannelOption.IP_TOS : 设置IP头部的Type-of-Service字段，用于描述IP包的优先级和QoS选项
 *              ChannelOption.ALLOW_HALF_CLOSURE : 一个连接的远端关闭时本地端是否关闭，默认值为False
 *              ChannelOption.SO_BROADCAST : 设置广播模式
 *              ChannelOption.IP_MULTICAST_ADDR : 对应IP参数IP_MULTICAST_IF，设置对应地址的网卡为多播模式
 *              ChannelOption.IP_MULTICAST_IF : 对应IP参数IP_MULTICAST_IF2，同上但支持IPV6
 *              ChannelOption.IP_MULTICAST_TTL : IP参数，多播数据报的time-to-live即存活跳数
 *
 *  @author: pickjob@126.com
 *  @date: 2022-12-29
 */
public class NettyShowCase {
    private static final Logger logger = LogManager.getLogger();
    private static final String SERVER = "localhost";
    private static final Integer PORT = 1080;
    private static final CountDownLatch serverReady = new CountDownLatch(1);

    public static void main(String[] args) {
        // 检测Netty资源泄露情况(ByteBuffer 未释放, 仅适用开发环境)
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        // 启动服务端
        new Thread(NettyShowCase::serverRunnable).start();
        // 启动客户端
        new Thread(NettyShowCase::clientRunnable).start();
    }

    private static void serverRunnable() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p
                                    // discard
                                    .addLast(new DiscardChannelHandler())
                            ;
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(PORT).sync();
            f.addListener(ff -> {
                logger.info("Server is running ...");
                serverReady.countDown();
            });
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p
                                    // discard
                                    .addLast(new DiscardClientHandler())
                            ;
                        }
                    });
            // Make the connection attempt.
            ChannelFuture f = b.connect(SERVER, PORT).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
