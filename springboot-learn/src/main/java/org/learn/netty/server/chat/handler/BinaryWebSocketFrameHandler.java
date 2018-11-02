package org.learn.netty.server.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
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
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> { // WebSocketFrame
    private Logger logger = LogManager.getLogger(this.getClass());

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 收到消息时调用 处理 BinaryWebSocketFrame
     * 将消息转发给其他客户端
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame binaryFrame) throws Exception {
        logger.info("BinaryWebSocketFrameHandler channelRead0");
        Channel channel = ctx.channel();
        ByteBuf content = binaryFrame.content();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.capacity(); i++) {
            sb.append(content.getByte(i));
        }
        channel.writeAndFlush(new TextWebSocketFrame(sb.toString()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("BinaryWebSocketFrameHandler handlerAdded");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("BinaryWebSocketFrameHandler channelActive");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("BinaryWebSocketFrameHandler channelReadComplete");
        ctx.flush();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("BinaryWebSocketFrameHandler handlerRemoved");
    }

    /**
     * 监听到客户端不活动时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("BinaryWebSocketFrameHandler channelInactive");
    }

    /**
     * 当Netty由于IO错误或者处理器在处理事件抛出异常时调用
     * 关闭连接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("BinaryWebSocketFrameHandler exceptionCaught");
        cause.printStackTrace();
    }
}