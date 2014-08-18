package cn.mob.poplar.core;

/**
 * Created by Administrator on 2014/8/7.
 */
public class BaseController {

    public byte[] execute(Object result) {
        if (result == null) {
            return null;
        }
        return result.toString().getBytes();
    }
}
