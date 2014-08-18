package cn.mob.poplar.core;

import cn.mob.poplar.util.StringUtils;
import cn.mob.poplar.util.Util;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2014/8/6.
 */
public class ServerHandler extends SimpleChannelUpstreamHandler {

    private static final Logger LOGGER = Logger.getLogger(ServerHandler.class);

    private Mapping registry;
    private ExecutorService worker;


    public ServerHandler(Mapping registry, ExecutorService worker) {
        this.registry = registry;
        this.worker = worker;
    }

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        HttpRequest request = (HttpRequest) e.getMessage();
        String uri = request.getUri();
        QueryStringDecoder decoder = new QueryStringDecoder(uri);
        String path = decoder.getPath();

        LOGGER.debug(String.format("[access] uri : %s, req : %s", request.getUri(), request.getHeaders()));

        if ("/favicon.ico".equals(path)) {
            return;
        }

        CMBean cmBean = registry.lookup(path);
        if (cmBean == null) {
            ActionWriter.writeError(ctx.getChannel(), HttpResponseStatus.NOT_FOUND);
            LOGGER.warn("[uri not found ] " + StringUtils.substringAfter(path, "/") + " not mapping !");
            return;
        }


        if (this.worker != null) {
            ActionTask task = new ActionTask(ctx, request, decoder, cmBean);
            this.worker.submit(task);
            return;
        }
        Util.execute(ctx, request, decoder, cmBean);
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
