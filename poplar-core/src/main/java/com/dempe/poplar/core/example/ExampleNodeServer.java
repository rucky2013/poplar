package com.dempe.poplar.core.example;

import com.dempe.poplar.core.PoplarBuilder;
import com.dempe.poplar.core.PoplarServer;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/10
 * Time: 21:06
 * To change this template use File | Settings | File Templates.
 */
public class ExampleNodeServer {

    public static void main(String[] args) throws ClassNotFoundException {

        new PoplarServer(new PoplarBuilder().setHost("127.0.0.1")
                .setPort(8889)
                .setNodeName("NodeDemo")
                .setBossThreadNum(8)
        ).startUp();
    }
}
