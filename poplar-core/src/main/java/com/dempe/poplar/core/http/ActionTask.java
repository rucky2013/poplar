package com.dempe.poplar.core.http;

import com.dempe.poplar.core.utils.Util;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

/**
 * Created by Administrator on 2014/8/6.
 */
public class ActionTask implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ActionTask.class);
    private ChannelHandlerContext ctx;
    private HttpRequest request;
    private QueryStringDecoder decoder;

    public ActionTask(ChannelHandlerContext ctx, HttpRequest request, QueryStringDecoder decoder) {
        this.ctx = ctx;
        this.decoder = decoder;
        this.request = request;
    }


    @Override
    public void run() {
       // Util.execute(ctx, request, decoder);
    }
}
