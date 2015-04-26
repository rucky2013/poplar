package com.dempe.poplar.common.zk;

import com.dempe.poplar.common.Constants;
import org.apache.log4j.Logger;

/**
 * 节点类
 * User: zhengdaxia
 * Date: 15/4/4
 * Time: 上午8:29
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    private final static Logger LOGGER = Logger.getLogger(Node.class);

    /**
     * 不同类型node通过name区分，
     */
    private String name = Constants.DEF_NODE_NAME;

    private String host;

    private int port;

    private String desc;

    public Node() {

    }

    public Node(String name, String host, int port, String desc) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.desc = desc;

    }

    public Node(String name, String host, int port) {
        this.name = name;
        this.port = port;
        this.host = host;
    }

    public Node(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", desc='" + desc + '\'' +
                '}';
    }
}
