package cn.mob.poplar.util;

import cn.mob.poplar.core.ActionWriter;
import cn.mob.poplar.core.BaseController;
import cn.mob.poplar.core.CMBean;
import javassist.NotFoundException;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0 date: 2014/8/18
 * @author: Dempe
 */
public class Util {
    public static Object[] getArgs(Class<?> type, String name, Map<String, List<String>> params) {
        Object[] objects = null;
        try {
            String names[] = ClassUtil.getMethodParamNames(type, name);
            objects = new Object[names.length];

            for (int i = 0; i < names.length; i++) {
                String value = getStringParameter(params, names[i]);
                objects[i] = value;
            }
        } catch (NotFoundException e) {

        } catch (ClassUtil.MissingLVException e) {

        }


        return objects;
    }

    public static String getStringParameter(Map<String, List<String>> params, String param) {
        List<String> paramList = params.get(param);
        if (paramList != null && paramList.size() > 0) {
            return paramList.get(0);
        }
        return null;
    }

    public static Map<String, List<String>> getParameter(HttpRequest request) {
        Map<String, List<String>> params = null;
        ChannelBuffer reqBuffer = request.getContent();
        byte[] message = reqBuffer.array();
        String content = new String(message);
        String[] strs = content.split("&");
        params = new HashMap<String, List<String>>();
        for (String s : strs) {
            String key = StringUtils.substringBefore(s, "=");
            String value = StringUtils.substringAfter(s, "=");
            List<String> list = new ArrayList<String>();
            String[] vals = value.split(",");
            for (String v : vals) {
                list.add(v);
            }
            params.put(key, list);
        }
        return params;

    }

    public static void execute(ChannelHandlerContext ctx, HttpRequest request, QueryStringDecoder decoder, CMBean cmBean) {
        String methodType = request.getMethod().getName();
        Map<String, List<String>> params = null;
        if ("POST".equals(methodType)) {
            Util.getParameter(request);
        } else if ("GET".equals(methodType)) {
            params = decoder.getParameters();
        }
        Object[] objects = Util.getArgs(cmBean.action.getClass(), cmBean.method.getName(), params);

        byte[] result = null;
        try {
            Object obj = cmBean.invoke(objects);
            result = new BaseController().execute(obj);
        } catch (InvocationTargetException e1) {
            ActionWriter.writeError(ctx.getChannel(), HttpResponseStatus.MULTI_STATUS);
            e1.printStackTrace();
            return;
        } catch (IllegalAccessException e1) {
            ActionWriter.writeError(ctx.getChannel(), HttpResponseStatus.MULTI_STATUS);
            e1.printStackTrace();
            return;
        }

        ActionWriter.writeResponse(ctx.getChannel(), result);

    }

}
