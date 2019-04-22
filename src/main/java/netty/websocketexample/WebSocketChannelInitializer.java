package netty.websocketexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import netty.socketexample.MyServerHandler;

/**
 * @author: fanbopeng
 * @Date: 2019/4/18 16:12
 * @Description:    客户端一旦跟服务端连接之后这个对象创建,  initChannel方法得到调用
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
                            //解码器

            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new ChunkedWriteHandler());//以块的方式去写的处理器
            pipeline.addLast(new HttpObjectAggregator(8192));//netty对http请求是分段的每100 ,将这些段聚合起来成为一个完整的请求或者响应
            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));//这个处理器这个运行一个websocket 的服务器负责websocket 的握手
                //对于websocket来说数据都是以frame的形式来传递的 分为共6种
        pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
