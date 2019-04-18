package netty.myChatServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author: fanbopeng
 * @Date: 2019/4/18 16:34
 * @Description:  客户端
 */
public class MyChatClient {


    public static void main(String[] args)  throws Exception {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        try {
            Bootstrap bootstrap =new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyChatServerInitializer());

            Channel channel = bootstrap.connect("localhost", 8899).channel();

            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                        channel.writeAndFlush(br.readLine());
            }

        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
