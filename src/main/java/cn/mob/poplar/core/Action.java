package cn.mob.poplar.core;

/**
 * Created by Administrator on 2014/8/6.
 */
public interface Action {

    byte[] execute(ActionContext context, byte[] message, Object result);
}
