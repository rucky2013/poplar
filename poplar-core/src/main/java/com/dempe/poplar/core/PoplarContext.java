package com.dempe.poplar.core;

import com.dempe.poplar.core.utils.PackageUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/20
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */
public class PoplarContext {

    private final Map<String,Object> map = new ConcurrentHashMap<String,Object>();

    public void init() throws ClassNotFoundException {
        String[] classNames = PackageUtils.findClassesInPackage("*");
        for (String className : classNames) {
            Object obj = Class.forName(className);

            map.put("",obj);
        }
    }
}
