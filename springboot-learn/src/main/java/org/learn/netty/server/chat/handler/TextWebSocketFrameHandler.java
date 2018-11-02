package org.learn.netty.server.chat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 名称	                    描述
 * BinaryWebSocketFrame	    二进制数据
 * TextWebSocketFrame	        文本数据
 * ContinuationWebSocketFrame	超大文本或者二进制数据
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> { // WebSocketFrame
    private Logger logger = LogManager.getLogger(this.getClass());

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 收到消息时调用
     * 将消息转发给其他客户端
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textFrame) throws Exception {
        // logger.info("Text - channelRead0 " + ctx.channel().id().asLongText());
        // Channel channel = ctx.channel();
        // System.out.println(channel.remoteAddress() + ": " + msg.text());
        // ctx.channel().writeAndFlush(new TextWebSocketFrame("来自服务端: " + LocalDateTime.now() + "message " + msg.text()));
        // TextWebSocketFrame

        logger.info("TextWebSocketFrameHandler channelRead0");

        Channel incoming = ctx.channel();
        for (Channel channel : channels) { // 遍历所有连接的客户端
            if (channel != incoming) {
                // 其他客户端
                channel.writeAndFlush(new TextWebSocketFrame("[匿名] " + textFrame.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you] " + textFrame.text()));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("TextWebSocketFrameHandler handlerAdded " + ctx.channel().id().asLongText());
        Channel incoming = ctx.channel();
        for (Channel channel : channels) { // 遍历所有连接的客户端
            // 广播加入消息
            channel.writeAndFlush(new TextWebSocketFrame("[欢迎] " + incoming.remoteAddress() + " 加入\n"));
        }
        channels.add(incoming); // 存入列表
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("TextWebSocketFrameHandler channelReadComplete ");
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("TextWebSocketFrameHandler channelActive ");
    }

    /**
     * 监听到客户端不活动时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("TextWebSocketFrameHandler channelInactive ");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("TextWebSocketFrameHandler handlerRemoved ");
        // channel 会自动从 ChannelGroup中删除
    }

    /**
     * 当Netty由于IO错误或者处理器在处理事件抛出异常时调用
     * 关闭连接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.info("TextWebSocketFrameHandler exceptionCaught ");
    }
}