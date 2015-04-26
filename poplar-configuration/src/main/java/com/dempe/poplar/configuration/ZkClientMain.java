package com.dempe.poplar.configuration;

import com.dempe.poplar.common.zk.ZkClient;
import org.apache.curator.framework.CuratorFramework;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/26
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
public class ZkClientMain {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = ZkClient.getClient();

        client.start();
        client.getChildren().forPath("conf");
    }
}
