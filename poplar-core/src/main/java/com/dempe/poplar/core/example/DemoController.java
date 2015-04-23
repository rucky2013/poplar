package com.dempe.poplar.core.example;


import com.dempe.poplar.core.support.Controller;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/10
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DemoController {

    @Inject
    private DemoService demoService;

    public String greet(@Param(name = "a") String a,@Param Integer b) {
        System.out.println(a);
        //demoService.greet();
        System.out.println(a);
        return "hello";

    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = DemoController.class.getMethods();
        for (Method method : methods) {
            //System.out.println(method);
            if (method.getName() == "greet") {
                System.out.println(method);
                Annotation[][] an = method.getParameterAnnotations();
                System.out.println(an.length);
                for (int i = 0; i < an.length; i++) {
                    for (int j = 0; j < an[i].length; j++) {
                        System.out.println(an[i][j].annotationType());
                        Param t = (Param) an[i][j];
                        System.out.println(method.getName() + "," + t.name());
                    }
                }

            }
        }
    }


}
