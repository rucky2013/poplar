package com.dempe.poplar.keeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/2
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
public class PoplarZookeeper {

    public static void main(String[] args) throws Exception {

        CuratorFramework    client = CuratorFrameworkFactory.builder().connectString("120.24.211.40:2180")
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace("poplar")
                .build();

        client.start();


        System.out.println("-----------------");
        System.out.println(client.getData().forPath("/zookeeper"));
       // client.create().forPath("/servers", "localhost:8080".getBytes());
//        byte[] bs = client.getData().forPath("/servers");
//        System.out.println(new String(bs));
//        System.out.println(client.getData().forPath("/servers"));
    }
}
