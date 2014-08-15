package cn.mob.poplar.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


public class ObjectFactory<T> {
    private Class<T> claxx;

    public ObjectFactory(Class<T> clazz) {
        this.claxx = clazz;
    }

    public T newInstance() throws InstantiationException, IllegalAccessException {
        T obj = this.claxx.newInstance();
        return obj;
    }

    public T newInstance(Class<?>[] argsClasses, Object[] constructorArgs) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<T> cons = this.claxx.getConstructor(argsClasses);
        return cons.newInstance(constructorArgs);
    }

    public T newInstanceAndBindProperties(Map<String, Object> propertys) throws InstantiationException, IllegalAccessException {
        T obj = this.claxx.newInstance();
        ObjectUtils.setProperties(this.claxx, propertys);
        return obj;
    }

    public T newInstanceAndBindProperties(Class<?>[] argsClasses, Object[] constructorArgs, Map<String, Object> propertys) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<T> cons = this.claxx.getConstructor(argsClasses);
        T obj = cons.newInstance(constructorArgs);
        ObjectUtils.setProperties(this.claxx, propertys);
        return obj;
    }

    public Constructor<?> getConstructor(Class<?>[] argsClasses) throws SecurityException, NoSuchMethodException {
        return this.claxx.getConstructor(argsClasses);
    }

    public Constructor<?>[] getConstructor() throws SecurityException, NoSuchMethodException {
        return this.claxx.getConstructors();
    }

    public void setProperties(T t, Map<String, Object> propertys) {
        ObjectUtils.setProperties(t, propertys);
    }

    public void setProperty(T t, String name, Object val) {
        ObjectUtils.setProperty(t, name, val);
    }

}
