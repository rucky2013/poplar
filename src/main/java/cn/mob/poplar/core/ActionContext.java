package cn.mob.poplar.core;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.*;

import java.net.InetSocketAddress;


/**
 * Created by Administrator on 2014/8/6.
 */
public class ActionContext {

    private HttpRequest request;
    private HttpResponse response;

    private ChannelHandlerContext ctx;

    public ActionContext(ChannelHandlerContext ctx, HttpRequest request) {
        this.request = request;
        this.ctx = ctx;
        this.response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    }

    public Channel getChannel() {
        return ctx.getChannel();
    }

    public String getRemoteAddress() {
        InetSocketAddress addr = (InetSocketAddress) ctx.getChannel().getRemoteAddress();
        return addr.getAddress().getHostAddress();
    }

    private String findAddress(String forwardFor) {
        if (forwardFor == null || forwardFor.length() == 0)
            return null;
        String[] addresses = forwardFor.split(",");
        for (String add : addresses) {
            if (!"unknown".equals(add))
                return add;
        }
        return null;
    }

    public String getMethod() {
        return request.getMethod().getName();
    }

    public String getUri() {
        return request.getUri();
    }

    public HttpResponse getHttpResponse() {
        return response;
    }
}
