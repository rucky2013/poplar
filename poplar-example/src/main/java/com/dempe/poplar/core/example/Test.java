package com.dempe.poplar.core.example;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/24
 * Time: 20:47
 * To change this template use File | Settings | File Templates.
 */

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class Test {
    public static void main(String[] args) {

        testReflectParamName();

    }

    /**
     * 反射获取方法参数名称
     */
    public static void testReflectParamName() {
        Class clazz = MyClass.class;
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
}

class MyClass {
    public String concatString(String param1, String param2) {
        return param1 + param2;
    }
}