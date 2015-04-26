package com.dempe.poplar.core.example;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/24
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Date;

public class Test2 {
    public static void main(String[] args) {

        testReflectParamName();
        // 反射获取方法参数注解
        testReflectMethodParamAnno();
    }

    /**
     * 反射获取方法参数名称
     */
    public static void testReflectParamName() {
        Class clazz = MyClass2.class;
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod("concatString");

            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                // exception
            }
            String[] paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++)
                paramNames[i] = attr.variableName(i + pos);
            // paramNames即参数名
            for (int i = 0; i < paramNames.length; i++) {
                System.out.println("参数名" + i + ":" + paramNames[i]);
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射获取方法参数注解
     */
    public static void testReflectMethodParamAnno() {
        Class clazz = MyClass2.class;
        try {
            // 使用jdk原生的反射方法
            Method m = clazz.getDeclaredMethod("datefomat",
                    new Class[]{Date.class});
            Annotation[][] annotations = m.getParameterAnnotations();
            System.out.println("jdk获取方法参数anno:" + annotations[0][0]);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod("datefomat");

            // 使用javassist的反射方法可以获得参数标注值
            Object[][] annotations = cm.getParameterAnnotations();
            DateFormat myAnno = (DateFormat) annotations[0][0];
            System.out.println("参数注解：" + myAnno.value());

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class MyClass2 {
    public String concatString(String param1, String param2) {
        return param1 + param2;
    }

    public String datefomat(@DateFormat("yyyy-MM-dd HH")
                            Date date1) {
        return date1.toString();
    }
}

// 注解类
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface DateFormat {
    String value() default "yyyy-MM-dd";
}