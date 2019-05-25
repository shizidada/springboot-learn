//package org.learn.backup.netty.server.chat;
//
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import io.netty.handler.timeout.IdleStateHandler;
//import org.learn.backup.netty.server.chat.handler.BinaryWebSocketFrameHandler;
//import org.learn.backup.netty.server.chat.handler.IdleHandler;
//import org.learn.backup.netty.server.chat.handler.TextWebSocketFrameHandler;
//
//import java.util.concurrent.TimeUnit;
//
//public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
//
//    @Override
//    protected void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline pipeline = ch.pipeline();
//
//        // HttpServerCodec: 针对 http 协议进行编解码
//        pipeline.addLast("httpServerCodec", new HttpServerCodec());
//
//        /**
//         * 作用是将一个Http的消息组装成一个完成的 HttpRequest 或者 HttpResponse，那么具体的是什么
//         * 取决于是请求还是响应, 该 Handler 必须放在 HttpServerCodec后的后面
//         */
//        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(10 * 1024 * 1024));
//
//        // ChunkedWriteHandler 分块写处理，文件过大会将内存撑爆
//        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
//
//        //用于处理 websocket, /ws 为访问 websocket 时的 uri
//        pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/app/websocket"));
//
//        /**
//         1）readerIdleTime：为读超时时间（即测试端一定时间内未接受到被测试端消息）;
//         2）writerIdleTime：为写超时时间（即测试端一定时间内向被测试端发送消息）
//         3）allIdleTime：所有类型的超时时间;
//         */
//        // 入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
//        pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
//
//        pipeline.addLast("TextWebSocketFrameHandler", new TextWebSocketFrameHandler());
//        pipeline.addLast("BinaryWebSocketFrameHandler", new BinaryWebSocketFrameHandler());
//        pipeline.addLast("IdleHandler", new IdleHandler());
//    }
//}
