package com.dempe.poplar.core.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/10
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.TYPE)
public @interface Controller {

    public String name()  default "";;


}
