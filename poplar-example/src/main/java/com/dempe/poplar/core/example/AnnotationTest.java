package com.dempe.poplar.core.example;

import com.dempe.poplar.common.anno.Controller;
import com.dempe.poplar.common.anno.Param;
import com.dempe.poplar.common.controller.ControllerMethod;
import com.dempe.poplar.common.controller.DefaultBeanClass;
import com.dempe.poplar.common.controller.DefaultControllerMethod;
import com.dempe.poplar.core.utils.PackageUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/24
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationTest {

    public static void main(String[] args) throws ClassNotFoundException {

        String[] classNames = PackageUtils.findClassesInPackage("com.dempe.*");
        for (String className : classNames) {
            Class<?> clzz = Class.forName(className);

            if (clzz.isAnnotationPresent(Controller.class)) {
                for (Method method : clzz.getMethods()) {

                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    Class[] parameterTypes = method.getParameterTypes();

                    int i=0;
                    for(Annotation[] annotations : parameterAnnotations){
                        Class parameterType = parameterTypes[i++];

                        for(Annotation annotation : annotations){
                            if(annotation instanceof Param){
                                Param myAnnotation = (Param) annotation;
                                System.out.println("param: " + parameterType.getName());
                                System.out.println("name : " + myAnnotation.name());
                            }
                        }
                    }

                }

            }

        }

    }
}
