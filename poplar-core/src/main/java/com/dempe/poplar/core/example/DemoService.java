package com.dempe.poplar.core.example;


import javax.enterprise.context.SessionScoped;


/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/17
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("ALL")
@SessionScoped
public class DemoService {

    public void greet(){
        System.out.println("hello");
    }
}
