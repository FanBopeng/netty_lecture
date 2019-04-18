package netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author: fanbopeng
 * @Date: 2019/4/18 16:17
 * @Description:
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter{


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){

             IdleStateEvent event=(IdleStateEvent)evt;

             String eventType=null;

             switch (event.state()){

                 case  READER_IDLE:
                     eventType="读空闲";
                     break;
                 case WRITER_IDLE:
                       eventType="写空闲";
                     break;
                 case ALL_IDLE:
                      eventType="读写都空闲";
                     break;
             }

            System.out.println(eventType);

             ctx.channel().close();
        }
    }
}
