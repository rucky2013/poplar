package cn.mob.poplar.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;


/**
 * Created by Administrator on 2014/8/6.
 */
public class ActionWriter {

    private static final Logger LOGGER = Logger.getLogger(ActionWriter.class);

    public static void writeResponse(Channel channel, byte[] responseMessage) {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        try {
            if (!channel.isWritable()) {
                return;
            }
            int length = 0;
            if (responseMessage != null) {
                ChannelBuffer buffer = ChannelBuffers.wrappedBuffer(responseMessage);
                response.setContent(buffer);
                length = response.getContent().writerIndex();
            }
            response.setHeader("Content-Type", "text/html; chareset=UTF-8");
            response.setHeader("Content-Length", String.valueOf(length));
            channel.write(response).addListener(ChannelFutureListener.CLOSE);
        } catch (Throwable throwable) {
            LOGGER.error(throwable);
        }

    }

    public static void writeError(Channel channel, HttpResponseStatus status) {
        try {
            if (!channel.isConnected() || !channel.isWritable()) {
                return;
            }
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
            response.setHeader(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
            JSONObject json = new JSONObject();
            json.put("status", status.getCode());
            json.put("message", status.getReasonPhrase());
            response.setContent(ChannelBuffers.copiedBuffer(json.toJSONString(), CharsetUtil.UTF_8));
            channel.write(response).addListener(ChannelFutureListener.CLOSE);

        } catch (Throwable throwable) {
            LOGGER.error(throwable);
        }
    }
}
