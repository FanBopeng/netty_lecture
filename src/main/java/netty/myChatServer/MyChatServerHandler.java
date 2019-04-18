package netty.myChatServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;

/**
 * @author: fanbopeng
 * @Date: 2019/4/18 16:17
 * @Description:
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //channel对象的集合
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * @Description //服务端收到任意一个客户端的消息,都会被调用
     * @Param [ctx, msg] ctx: netty上下文信息,  msg:客户端真正接受过来的对象
     * @return void
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch->{
               if (channel!=ch){
                   ch.writeAndFlush(channel.remoteAddress()+"发送的消息"+msg);
               }else {
                   ch.writeAndFlush("自己"+msg);
               }
        });

    }
    /**
     * @Description //TODO每有一个客户端连接 就会执行该方法
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //广播
        channelGroup.writeAndFlush("服务器-"+channel.localAddress()+"上线了");

        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("服务器"+channel.localAddress()+"已经下线");
        channelGroup.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"下线");
    }

    /**
     * @Description //如果出现异常怎样处理
     * @Param [ctx, cause]
     * @return void
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
