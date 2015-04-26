package com.dempe.poplar.core;

import com.dempe.poplar.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/10
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class PoplarBuilder {

    /**
     * 默认不注册zookeeper
     */
    private boolean registerKeeper = false;

    private String nodeName = Constants.DEF_NODE_NAME;

    private String host = "0.0.0.0";

    private int port = 8988;


    /**
     * TODO 根据服务器内核确定线程数量
     */
    private int bossThreadNum = 4;

    private int workThreadNum = 8;


    public int getPort() {
        return port;
    }

    public PoplarBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public String getHost() {
        return host;
    }

    public PoplarBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public PoplarBuilder registerKeeper() {
        this.registerKeeper = true;
        return this;
    }

    public PoplarBuilder setNodeName(String name) {
        this.nodeName = name;
        return this;
    }

    public boolean isRegisterKeeper() {
        return registerKeeper;
    }

    public PoplarBuilder setRegisterKeeper(boolean registerKeeper) {
        this.registerKeeper = true;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }


    public int getBossThreadNum() {
        return bossThreadNum;
    }

    public PoplarBuilder setBossThreadNum(int bossThreadNum) {
        this.bossThreadNum = bossThreadNum;
        return this;
    }

    public int getWorkThreadNum() {
        return workThreadNum;
    }

    public PoplarBuilder setWorkThreadNum(int workThreadNum) {
        this.workThreadNum = workThreadNum;
        return this;
    }
}
