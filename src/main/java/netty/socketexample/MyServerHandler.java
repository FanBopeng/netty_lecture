package netty.socketexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author: fanbopeng
 * @Date: 2019/4/18 16:17
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * @Description //TODO
     * @Param [ctx, msg] ctx: netty上下文信息,  msg:客户端真正接受过来的对象
     * @return void
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println( ctx.channel().remoteAddress()+","+msg);
        ctx.channel().writeAndFlush("from server:"+ UUID.randomUUID());   //write()和flush()方法的简写

    }

    /**
     * @Description //如果出现异常怎样处理
     * @Param [ctx, cause]
     * @return void
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
