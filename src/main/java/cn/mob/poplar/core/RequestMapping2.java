package cn.mob.poplar.core;

import cn.mob.poplar.anno.Get;
import cn.mob.poplar.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @version 1.0 date: 2014/8/18
 * @author: Dempe
 */
@Component
public class RequestMapping2{

    private static final Logger LOGGER = Logger.getLogger(RequestMapping.class);

    @Resource
    private RequestMapping requestMapping;






}
