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

public class IdleHandler extends SimpleChannelInboundHandler<Object> {
    private Logger logger = LogManager.getLogger(this.getClass());

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 空闲次数
     */
    private int idleCount = 1;
    /**
     * 发送次数
     */
    private int count = 1;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String text = (String) msg;

        logger.info("IdleHandler channelRead0 " + "第" + count + "次" + ",服务端接受的消息:" + text);
        if ("idle".equals(text)) {
            //如果是心跳命令，则发送给客户端; 否则什么都不做
            ctx.write(new TextWebSocketFrame("服务端成功收到心跳信息 : " + text));
            ctx.flush();
        }
        count++;
    }

    /**
     * 超时处理
     * 如果5秒没有接受客户端的心跳，就触发;
     * 如果超过两次，则直接关闭;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        //  logger.info("IdleHandler - userEventTriggered ");
        Channel incoming = ctx.channel();
        for (Channel channel : channels) { // 遍历所有连接的客户端
            // 广播加入消息
            if (channel != incoming) {
                incoming.writeAndFlush(new TextWebSocketFrame("[" + channel.remoteAddress() + "] 处于 10 秒没有回消息 ！！"));
            }
        }
//        if (obj instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) obj;
//            // 如果读通道处于空闲状态，说明没有接收到心跳命令
//            if (IdleState.READER_IDLE.equals(event.state())) {
//                logger.info("已经5秒没有接收到客户端的信息了");
//                if (idleCount > 2) {
//                    logger.info("关闭这个不活跃的 channel");
//                    ctx.channel().close();
//                }
//                idleCount++;
//            }
//        } else {
//            super.userEventTriggered(ctx, obj);
//        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        if (channels.isEmpty()) {
            incoming.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]"));
            logger.info("IdleHandler --------------------------- handlerAdded ");
        }
        for (Channel channel : channels) { // 遍历所有连接的客户端
            // 广播加入消息
            if (channel != incoming) {
                incoming.writeAndFlush(new TextWebSocketFrame("[欢迎] " + channel.remoteAddress() + " 加入\n"));
            }
        }
        channels.add(incoming); // 存入列表
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("IdleHandler channelReadComplete ");
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("IdleHandler channelActive ");
    }

    /**
     * 监听到客户端不活动时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("IdleHandler channelInactive ");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("IdleHandler handlerRemoved ");
        Channel incoming = ctx.channel();
        // 广播离开消息
        for (Channel channel : channels) { // 遍历所有连接的客户端
            // 广播加入消息
            if (channel != incoming) {
                incoming.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress() + " 离开\n"));
            }
        }
        // channel 会自动从 ChannelGroup中删除
    }

    /**
     * 当Netty由于IO错误或者处理器在处理事件抛出异常时调用
     * 关闭连接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("IdleHandler exceptionCaught ");
        cause.printStackTrace();
    }
}
