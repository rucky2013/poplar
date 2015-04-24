package com.dempe.poplar.core.utils;

import com.dempe.poplar.common.anno.Param;
import com.dempe.poplar.common.controller.ControllerMethod;
import com.dempe.poplar.core.http.ActionWriter;
import javassist.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @version 1.0 date: 2014/8/18
 * @author: Dempe
 */
public class Util {
    public static Object[] getArgs(Class<?> type, Method method, Map<String, List<String>> params) throws Exception {
        Object[] objects = null;
        try {
            String names[] = ClassUtil.getMethodParamNames(type, method.getName());
            objects = new Object[names.length];
            Class<?>[] clazz = method.getParameterTypes();

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            int i = 0;
            for (Annotation[] annotations : parameterAnnotations) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Param) {
                        Param myAnnotation = (Param) annotation;

                        String name = StringUtils.equals(myAnnotation.name(), "") ? names[i] : myAnnotation.name();

                        String value = getStringParameter(params, name);

                        String typeName = clazz[i].getName();
                        if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
                            objects[i] = Integer.parseInt(value);
                        } else if (typeName.equals("long") || typeName.equals("java.lang.Long")) {
                            objects[i] = Long.parseLong(value);
                        } else if (typeName.equals("java.util.Date")) {

                        } else if (typeName.equals("java.lang.String")) {
                            objects[i] = value;
                        } else {

                            throw new Exception();
                        }
                    }
                }
                i++;
            }


            System.out.println(Arrays.toString(objects));
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

    public static void execute(ChannelHandlerContext ctx, HttpRequest request, QueryStringDecoder decoder, ControllerMethod method) throws Exception {
        String methodType = request.getMethod().getName();
        Map<String, List<String>> params = null;
        if ("POST".equals(methodType)) {
            Util.getParameter(request);
        } else if ("GET".equals(methodType)) {
            params = decoder.getParameters();
        }
        System.out.println("---------");

        byte[] result = null;
        try {

            Object[] objects = Util.getArgs(method.getController().getType(), method.getMethod(), params);
            method.getMethod().getGenericParameterTypes();
            result = method.getMethod().invoke(method.getController().getType().newInstance(), objects).toString().getBytes();
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
