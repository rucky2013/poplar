package com.dempe.poplar.core.utils;

import com.dempe.poplar.core.http.ActionWriter;
import com.dempe.poplar.core.support.ControllerMethod;
import org.apache.commons.lang.StringUtils;
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
//        try {
//            String names[] = ClassUtil.getMethodParamNames(type, name);
//            objects = new Object[names.length];
//
//            for (int i = 0; i < names.length; i++) {
//                String value = getStringParameter(params, names[i]);
//                objects[i] = value;
//            }
//        } catch (NotFoundException e) {
//
//        } catch (ClassUtil.MissingLVException e) {
//
//        }


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

    public static void execute(ChannelHandlerContext ctx, HttpRequest request, QueryStringDecoder decoder,ControllerMethod method) throws InstantiationException {
        String methodType = request.getMethod().getName();
        System.out.println("methodType===>"+methodType);
        Map<String, List<String>> params = null;
        if ("POST".equals(methodType)) {
            Util.getParameter(request);
        } else if ("GET".equals(methodType)) {
            params = decoder.getParameters();
        }

        byte[] result = null;
        try {
            method.getMethod().getGenericParameterTypes();
            result = method.getMethod().invoke(method.getController().getType().newInstance()).toString().getBytes();
            System.out.println("===result==>"+result);
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
