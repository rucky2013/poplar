package com.dempe.poplar.core.example;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/23
 * Time: 下午11:44
 * To change this template use File | Settings | File Templates.
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Path2 {
    String value() default "";
}
