package org.learn.netty.server.simple;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 添加处理类
        // 使用'\r''\n'分割帧
        pipeline.addLast("framer",
                new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 解码、编码器
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        // 处理器
        pipeline.addLast("handler", new SimpleChatServerHandler());

        System.out.println(ch.remoteAddress() + "连接上");
    }
}
