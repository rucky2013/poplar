package com.dempe.poplar.core;

import com.dempe.poplar.core.utils.PackageUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/20
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */
public class PoplarContext {

    public void init(){
        String[] classNames = PackageUtils.findClassesInPackage("*");
        for (String className : classNames) {

        }
    }
}
