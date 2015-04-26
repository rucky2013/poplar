package com.dempe.poplar.configuration;

import com.alibaba.fastjson.JSONObject;
import com.dempe.poplar.common.zk.ZkClient;
import org.apache.curator.framework.CuratorFramework;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/26
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
public class ConfService {

    CuratorFramework client = ZkClient.getClient();

    public ConfService() {
        client = ZkClient.getClient();

    }

    public List<String> listServices() throws Exception {
        return client.getChildren().watched().forPath(File.separator);

    }

    public List<String> listServers(String serviceName) throws Exception {
        return client.getChildren().watched().forPath(File.separator + serviceName);
    }

    public String getServerConf(String serviceName, String serverName) throws Exception {
        return new String(client.getData().forPath(File.separator + serviceName + File.separator + serverName));
    }
}
