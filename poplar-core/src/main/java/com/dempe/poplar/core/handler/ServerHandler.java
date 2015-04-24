package com.dempe.poplar.core.handler;

import com.dempe.poplar.common.controller.ControllerMethod;
import com.dempe.poplar.core.PoplarContext;
import com.dempe.poplar.core.http.ActionTask;
import com.dempe.poplar.core.http.ActionWriter;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/10
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class ServerHandler extends SimpleChannelUpstreamHandler {

    private static final Logger LOGGER = Logger.getLogger(ServerHandler.class);

    /**
     * TODO 提供配置
     */
    private ExecutorService worker = Executors.newFixedThreadPool(8);

    private PoplarContext context;

    public ServerHandler(PoplarContext context) {
        this.context = context;
    }

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws InstantiationException {
        HttpRequest request = (HttpRequest) e.getMessage();
        String uri = request.getUri();
        QueryStringDecoder decoder = new QueryStringDecoder(uri);
        String path = decoder.getPath();

        LOGGER.debug(String.format("[access] uri : %s, req : %s", request.getUri(), request.getHeaders()));


        if ("/favicon.ico".equals(path)) {
            return;
        }

        ControllerMethod method = context.parse(org.apache.commons.lang.StringUtils.substringBefore(uri,"?"));
        ActionTask task = new ActionTask(ctx, request, decoder,method);
        this.worker.submit(task);


    }


    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent event) {
        Throwable cause = event.getCause();
        try {
            if (cause instanceof TooLongFrameException) {
                ActionWriter.writeError(ctx.getChannel(), HttpResponseStatus.BAD_REQUEST);
                return;
            }
            cause.printStackTrace();
        } catch (Throwable throwable) {
            LOGGER.error(throwable);

        }
    }


    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent event) throws Exception {
        super.channelOpen(ctx, event);
    }


}
