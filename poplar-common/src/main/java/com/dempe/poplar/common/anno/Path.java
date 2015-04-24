package com.dempe.poplar.common.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/24
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Path {
    String[] value() default {};
}
