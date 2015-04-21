package com.dempe.poplar.core.example;



import com.dempe.poplar.core.support.Controller;

import javax.inject.Inject;
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

    public String greet() {
        //demoService.greet();
        return "hello";

    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = DemoController.class.getMethods();
        for (Method method : methods) {
            //System.out.println(method);
            if(method.getName()=="greet"){
                System.out.println(method);
                Object obj = method.invoke(new DemoController());
                System.out.println(obj);
            }
        }
    }
}
