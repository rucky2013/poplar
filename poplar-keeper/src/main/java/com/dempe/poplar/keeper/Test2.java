package com.dempe.poplar.keeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/4
 * Time: 上午12:45
 * To change this template use File | Settings | File Templates.
 */
public class Test2 {

    //private final static String PATH = "/servers/http";
    private final static String PATH = "";


    public static void main(String[] args) throws Exception {

        CuratorFramework client = getClient();

        client.start();

//        client.create().forPath(PATH+"/server1","12".getBytes());
//        client.create().forPath(PATH+"/server2","22".getBytes());

        client.create().creatingParentsIfNeeded().forPath("/test/123","222".getBytes());

        List<String> ls = client.getChildren().forPath("");

        for (String l : ls) {
            System.out.println(l);
        }

        NodeCache nodeCache = new NodeCache(client,PATH);


        nodeCache.start();

        System.out.println();

    }

    public static CuratorFramework getClient(){
        return CuratorFrameworkFactory.builder()
                .namespace("poplar")
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .connectString("120.24.211.40:2181").build();

    }
}
