package cn.mob.poplar.core;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2014/8/13.
 */
public class RequestRegistry implements Registry {

    private static final Logger LOGGER = Logger.getLogger(RequestRegistry.class);

    private final Map<String, CMBean> mapping = new HashMap<String, CMBean>();

    public void mapping(String uri, CMBean action) {
        mapping.put(uri, action);
    }


    public CMBean lookup(String uri) {
        return mapping.get(uri);
    }

    public void mapping(ApplicationContext context) {
        Map<String, Object> map = context.getBeansWithAnnotation(Controller.class);
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key);
            Object obj = map.get(key);
            String controller_name = obj.getClass().getSimpleName().toLowerCase().replace("controller", "");
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                mapping.put("/" + controller_name + "/" + method.getName(), new CMBean(obj, method));
            }

        }

    }


}
