package com.dempe.poplar.configuration;

import com.dempe.poplar.core.PoplarBuilder;
import com.dempe.poplar.core.PoplarServer;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/26
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
public class ConfMain {
    public static void main(String[] args) throws Exception {
        new PoplarServer(new PoplarBuilder().setHost("127.0.0.1")
                //.setRegisterKeeper(true)
                .setPort(8889)
                .setNodeName("NodeDemo")
                .setBossThreadNum(8)
        ).startUp();
    }
}
