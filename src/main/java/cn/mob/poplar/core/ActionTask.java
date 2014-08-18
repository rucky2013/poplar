package cn.mob.poplar.core;

import cn.mob.poplar.util.Util;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

/**
 * Created by Administrator on 2014/8/6.
 */
public class ActionTask implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ActionTask.class);
    private CMBean cmBean;
    private ChannelHandlerContext ctx;
    private HttpRequest request;
    private QueryStringDecoder decoder;

    public ActionTask(ChannelHandlerContext ctx, HttpRequest request, QueryStringDecoder decoder, CMBean cmBean) {
        this.ctx = ctx;
        this.cmBean = cmBean;
        this.decoder = decoder;
        this.request = request;
    }


    @Override
    public void run() {
        Util.execute(ctx, request, decoder, cmBean);
    }
}
