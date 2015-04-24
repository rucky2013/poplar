package com.dempe.poplar.core.http;

import com.dempe.poplar.common.controller.ControllerMethod;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import static com.dempe.poplar.core.utils.Util.execute;

/**
 * Created by Administrator on 2014/8/6.
 */
public class ActionTask implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ActionTask.class);
    private ChannelHandlerContext ctx;
    private HttpRequest request;
    private QueryStringDecoder decoder;
    private ControllerMethod method;

    public ActionTask(ChannelHandlerContext ctx, HttpRequest request, QueryStringDecoder decoder,ControllerMethod method) {
        this.ctx = ctx;
        this.decoder = decoder;
        this.request = request;
        this.method = method;
    }

    /**
     * TODO 统一的异常处理
     */

    @Override
    public void run() {

        try {
            execute(ctx, request, decoder, method);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
