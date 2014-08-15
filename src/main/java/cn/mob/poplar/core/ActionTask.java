package cn.mob.poplar.core;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2014/8/6.
 */
public class ActionTask implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ActionTask.class);
    private CMBean cmBean;
    private ActionContext context;
    private byte[] message;
    private Object[] objs;

    public ActionTask(ActionContext context, CMBean cmBean, byte[] message) {
        this.context = context;
        this.cmBean = cmBean;
        this.message = message;
    }

    public ActionTask(ActionContext context, CMBean cmBean, byte[] message, Object[] objs) {
        this.context = context;
        this.cmBean = cmBean;
        this.message = message;
        this.objs = objs;
    }


    @Override
    public void run() {
        try {
            Object obj = cmBean.method.invoke(cmBean.action, objs);
            byte[] result = new BaseController().execute(context, message, obj);
            ActionWriter.writeResponse(context.getChannel(), context.getHttpResponse(), result);
        } catch (Throwable throwable) {
            LOGGER.error(throwable);
        }
    }
}
