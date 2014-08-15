package cn.mob.poplar.core;

/**
 * Created by Administrator on 2014/8/6.
 */
public interface Registry {

    void mapping(String uri, CMBean cmBean);

    CMBean lookup(String uri);
}
