package com.dempe.poplar.core.node;

import com.dempe.poplar.core.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/4
 * Time: 上午8:39
 * To change this template use File | Settings | File Templates.
 */
public class ZkClient {


    public static CuratorFramework getClient(){
        return CuratorFrameworkFactory.builder()
                .namespace(Constants.NAMESPACE)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .connectString(Constants.ZK_CONNECT_STR)
                .build();

    }
}
