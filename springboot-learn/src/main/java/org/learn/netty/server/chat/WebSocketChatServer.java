//package org.learn.netty.server.chat;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//
//public class WebSocketChatServer {
//
//    private static final int PORT = 8080;
//    private static final String HOST = "127.0.0.1";
//
//    public static void main(String[] args) throws Exception {
//
//        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap server = new ServerBootstrap();
//            server.group(bossGroup, workerGroup)
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
//            ChannelFuture f = server.bind(HOST, PORT).sync();
//
//            // Wait until the server socket is closed.
//            // In this example, this does not happen, but you can do that to gracefully
//            // shut down your server.
//            f.channel().closeFuture().sync();
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//            System.out.println("WebSocketChatServer 关闭了");
//        }
//    }
//}