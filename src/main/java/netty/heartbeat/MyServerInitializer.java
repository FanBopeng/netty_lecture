package netty.heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author: fanbopeng
 * @Date: 2019/4/18 16:12
 * @Description:    客户端一旦跟服务端连接之后这个对象创建,  initChannel方法得到调用
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        //心跳检测器
        pipeline.addLast(new IdleStateHandler(1,2,3, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());


    }
}
