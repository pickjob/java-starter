package app.framework.netty;


import app.framework.netty.client.DiscardClientHandler;
import app.framework.netty.server.DiscardServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
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
 *      责任链模式:
 *          io.netty.channel.ChannelPipeline: ChannelHandler 流水线 处理Socket 读取、写入
 *          io.netty.channel.ChannelHandlerContext: Pipeline上下文
 *              事件触发:
 *                  fireChannelRegistered / fireChannelUnregistered
 *                  fireChannelActive / fireChannelInactive
 *                  fireUserEventTriggered
 *                  fireChannelRead
 *              其他方法:
 *                  read / write / flush
 *          io.netty.channel.ChannelHandler: Handler 接口
 *              handlerAdded / handlerRemoved: 事件回调
 *              exceptionCaught: 异常事件回调
 *              io.netty.channel.ChannelInboundHandler: 读操作
 *                  channelRegistered / channelUnregistered
 *                  channelActive / channelInactive
 *                  channelRead / channelReadComplete: 读事件回调
 *                  userEventTriggered: 自定义事件回调
 *              io.netty.channel.ChannelOutboundHandler: 写操作
 *                  bind / close
 *                  connect / disconnect
 *                  deregister
 *                  write: 写事件回调
 *              主要实现类:
 *                  io.netty.channel.ChannelInboundHandlerAdapter: 流入处理
 *                      userEventTriggered: 自定义消息回调
 *                      io.netty.handler.codec.ByteToMessageDecoder: 解码器
 *                          io.netty.handler.codec.LineBasedFrameDecoder: '\n', '\r\n' 数据帧分割
 *                          io.netty.handler.codec.DelimiterBasedFrameDecoder: 指定分隔符  数据帧分割
 *                              delimiter: 分割字符
 *                          io.netty.handler.codec.FixedLengthFrameDecoder: 固定长度  数据帧分割
 *                              frameLength: 固定长度
 *                          io.netty.handler.codec.LengthFieldBasedFrameDecoder: 指定长度 数据帧分割
 *                              lengthFieldOffset: 长度字段偏移
 *                              lengthFieldLength: 长度字段长度
 *                              lengthAdjustment: 长度字段数值修正(字节数)
 *                              initialBytesToStrip: 去除初始字段长度
 *                      io.netty.channel.SimpleChannelInboundHandler<I>: 处理指定类型消息, 符合条件自动释放
 *                          channelRead0: 处理指定消息
 *                      io.netty.handler.codec.MessageToMessageDecoder<I>: 消息转换消息, 符合条件自动释放
 *                          decode: 消息转换
 *                      io.netty.channel.SimpleUserEventChannelHandler<I>: 处理指定自定义类型消息, 符合条件自动释放
 *                          eventReceived: 处理自定义消息
 *                      io.netty.handler.timeout.ReadTimeoutHandler: 触发异常  ReadTimeoutException
 *                          timeoutSeconds
 *                  io.netty.channel.ChannelOutboundHandlerAdapter: 流出处理
 *                      io.netty.handler.codec.MessageToByteEncoder<I>: 编码器
 *                      io.netty.handler.codec.MessageToMessageEncoder<I>: 消息转换消息, 符合条件自动释放
 *                          encode: 消息转换
 *                          io.netty.handler.codec.string.LineEncoder: '\n', '\r\n', 自定义字符 数据帧分割
 *                          io.netty.handler.codec.LengthFieldPrepender: 初始加入字段长度
 *                              lengthFieldLength: 长度字段长度
 *                              lengthIncludesLengthFieldLength：长度字段长度是否包含长度
 *                              lengthAdjustment: 长度字段修正
 *                          io.netty.handler.timeout.WriteTimeoutHandler: 触发异常 WriteTimeoutException
 *                              timeoutSeconds
 *                  io.netty.channel.ChannelDuplexHandler: 双向处理
 *                      io.netty.handler.codec.MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN>: 编解码
 *                          encode
 *                          decode
 *                      io.netty.handler.logging.LoggingHandler: 事件日志记录
 *                      io.netty.handler.timeout.IdleStateHandler: 心跳检测, 触发 IdleStateEvent 事件
 *                          readerIdleTime
 *                          writerIdleTime
 *                          allIdleTime
 *                      io.netty.handler.flow.FlowControlHandler: 一次只产生一个消息给下一个ChannelHandler
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
 *  @date: 2024-09-04
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
                            p.addLast(new DiscardServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(PORT).sync();
            f.addListener(ff -> {
                logger.info("Server is running ...");
                serverReady.countDown();
            });
            f.channel().closeFuture().sync();
        } catch (Exception e){
            logger.error(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private static void clientRunnable() {
        try {
            serverReady.await();
        } catch (Exception e) {
            logger.error(e);
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
                            ch.pipeline().addLast(new DiscardClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(SERVER, PORT).sync();
            f.channel().closeFuture().sync();
        } catch(Exception e) {
            logger.error(e);
        } finally {
            group.shutdownGracefully();
        }
    }
}