package cn.mob.poplar.core;

/**
 * Created by Administrator on 2014/8/7.
 */
public class BaseController implements Action {
    @Override
    public byte[] execute(ActionContext context, byte[] message, Object result) {
        if (result == null) {
            return null;
        }
        return result.toString().getBytes();
    }
}
