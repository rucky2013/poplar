package cn.mob.poplar.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/8/7.
 */
public class CMBean {
    public Object action;
    public Method method;

    public CMBean(Object action, Method method) {
        this.action = action;
        this.method = method;


    }

    public String toString() {
        return "className : " + action.getClass().getSimpleName() + " methodName : " + method.getName();
    }

    public Object invoke(Object[] objs) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(action, objs);
    }
}
