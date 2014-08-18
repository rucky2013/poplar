package cn.mob.poplar.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @version 1.0 date: 2014/8/15
 * @author: Dempe
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Get {
    /**
     * All paths that will be mapped to an annotated Controller method.
     */
    String value() default "";
}
