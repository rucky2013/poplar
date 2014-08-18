package cn.mob.poplar.core;

import cn.mob.poplar.util.ClassUtil;
import cn.mob.poplar.util.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2014/8/6.
 */
public class ServerHandler extends SimpleChannelUpstreamHandler {

    private static final Logger LOGGER = Logger.getLogger(ServerHandler.class);
    private Registry registry;
    private ExecutorService worker;
    private AtomicInteger counter = new AtomicInteger();

    public ServerHandler(Registry registry, ExecutorService worker) {
        this.registry = registry;
        this.worker = worker;
    }

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        HttpRequest request = (HttpRequest) e.getMessage();
        String uri = request.getUri();
        QueryStringDecoder decoder = new QueryStringDecoder(uri);
        String path = decoder.getPath();
        if ("/favicon.ico".equals(path)) {
            return;
        }
        Map<String, List<String>> params = decoder.getParameters();
        CMBean cmBean = registry.lookup(path);

        if (cmBean == null) {
            ActionWriter.writeError(ctx.getChannel(), HttpResponseStatus.BAD_REQUEST);
            LOGGER.warn("[uri not found ] " + StringUtils.substringAfter(path, "/") + " not mapping !");
            return;
        }
        try {
            ChannelBuffer reqBuffer = request.getContent();
            byte[] message = reqBuffer.array();
            ActionContext context = new ActionContext(ctx, request);
            LOGGER.debug(String.format("[access] uri : %s, ip : %s, req : %s", request.getUri(),
                    context.getRemoteAddress(), request.getHeaders()));

            String names[] = ClassUtil.getMethodParamNames(cmBean.action.getClass(), cmBean.method.getName());
            Object[] objs = new Object[names.length];
            for (int i = 0; i < names.length; i++) {
                String value = getStringParameter(params, names[i]);
                objs[i] = value;
            }

            if (this.worker != null) {
                ActionTask task = new ActionTask(context, cmBean, message, objs);
                this.worker.submit(task);
                return;
            }

            Object obj = cmBean.invoke(objs);

            byte[] result = new BaseController().execute(context, message, obj);
            ActionWriter.writeResponse(ctx.getChannel(), context.getHttpResponse(), result);
        } catch (Throwable throwable) {
            ActionWriter.writeError(ctx.getChannel(), HttpResponseStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error(throwable);
        }
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
        counter.incrementAndGet();
    }

    public String getStringParameter(Map<String, List<String>> params, String param) {
        List<String> paramList = params.get(param);
        if (paramList != null && paramList.size() > 0) {
            return paramList.get(0);
        }
        return null;
    }


}
