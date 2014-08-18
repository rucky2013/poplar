package cn.mob.poplar.core;

import cn.mob.poplar.anno.Get;
import cn.mob.poplar.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2014/8/13.
 */
@Component
public class RequestRegistry implements Registry, ApplicationContextAware {

    private static final Logger LOGGER = Logger.getLogger(RequestRegistry.class);

    private final Map<String, CMBean> mapping = new HashMap<String, CMBean>();

    private ApplicationContext applicationContext;

    public void mapping(String uri, CMBean action) {
        mapping.put(uri, action);
    }

    public CMBean lookup(String uri) {
        return mapping.get(uri);
    }

    public void init() {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(Controller.class);
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object obj = map.get(key);

            String pre_uri = obj.getClass().getAnnotation(Controller.class).value();
            if (StringUtils.isBlank(pre_uri)) {
                pre_uri = obj.getClass().getSimpleName().toLowerCase().replace("controller", "");
            }
            pre_uri = formatURI(pre_uri);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                Get get = method.getAnnotation(Get.class);
                String uri = "";
                if (get != null && StringUtils.isNotEmpty(get.value())) {
                    uri = get.value();
                    uri = StringUtils.substringAfter(uri, "/");
                } else {
                    uri = method.getName();
                }
                mapping.put(pre_uri + "/" + uri, new CMBean(obj, method));
            }
        }
    }

    private String formatURI(String pre_uri) {
        if (!pre_uri.startsWith("/")) {
            pre_uri = "/" + pre_uri;
        }
        if (pre_uri.endsWith("/")) {
            pre_uri = StringUtils.substringBefore(pre_uri, "/");
        }
        return pre_uri;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
