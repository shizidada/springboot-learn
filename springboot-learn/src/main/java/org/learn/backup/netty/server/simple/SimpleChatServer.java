//package org.learn.backup.netty.server.simple;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//import org.learn.backup.netty.server.chat.WebSocketChannelInitializer;
//
//public class SimpleChatServer {
//    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
//
//    public static void main(String[] args) throws Exception {
//
//        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class) // 设置如何接受连接
//
//                    .handler(new LoggingHandler(LogLevel.INFO))
//
//                    .childHandler(new WebSocketChannelInitializer()) // 配置Channel
//
//                    .option(ChannelOption.SO_BACKLOG, 128) // 设置缓冲区
//
//                    .childOption(ChannelOption.SO_KEEPALIVE, true); // 启用心跳机制
//
//            // Bind and start to accept incoming connections.
//            ChannelFuture f = b.bind(PORT).sync();
//
//            // Wait until the server socket is closed.
//            // In this example, this does not happen, but you can do that to gracefully
//            // shut down your server.
//            f.channel().closeFuture().sync();
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//            System.out.println("NettyServer 关闭了");
//        }
//    }
//}