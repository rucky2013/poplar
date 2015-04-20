package com.dempe.poplar.core;

import com.dempe.poplar.core.support.*;
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
    RoutesParser parser = new PathAnnotationRoutesParser(new DefaultRouter());
    private Router router = new DefaultRouter();

    public void init() throws ClassNotFoundException {
        String[] classNames = PackageUtils.findClassesInPackage("com.dempe.*");
        for (String className : classNames) {
            Class<?> clzz =Class.forName(className);
            clzz.getAnnotation(Controller.class);
            if(clzz.isAnnotationPresent(Controller.class)){
                for (Route route : parser.rulesFor(Class.forName(className))) {
                    router.add(route);
                }
            }

        }
    }


    public static void main(String[] args) throws ClassNotFoundException {
        PoplarContext context = new PoplarContext();
        context.init();
    }
}
