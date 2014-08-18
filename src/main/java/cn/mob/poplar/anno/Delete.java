package cn.mob.poplar.anno;

import java.lang.annotation.*;

/**
 * @version 1.0 date: 2014/8/15
 * @author: Dempe
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    /**
     * All paths that will be mapped to an annotated Controller method.
     */
    String value() default "";
}
