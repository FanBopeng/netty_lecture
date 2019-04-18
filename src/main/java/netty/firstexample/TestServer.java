package netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: fanbopeng
 * @Date: 2019/4/18 14:32
 * @Description:
 */
public class  TestServer {


    public static void main(String[] args) throws InterruptedException {
        //事件循环组         两个死循环
        EventLoopGroup bossGroup =new NioEventLoopGroup();      //不断接受客户端连接

        EventLoopGroup workerGroup=new NioEventLoopGroup();     //接受客户端连接后 进行处理
            try {


                ServerBootstrap serverBootstrap=new ServerBootstrap();
                serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                        childHandler(new TestServerInitializer());  //定义子处理器

                ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();

                channelFuture.channel().closeFuture().sync();
            }finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
    }
}
