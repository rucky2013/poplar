package cn.mob.poplar.util;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2014/8/6.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ACTION {
    abstract String path();
}
