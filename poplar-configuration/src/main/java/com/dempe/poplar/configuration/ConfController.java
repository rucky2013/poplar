package com.dempe.poplar.configuration;

import com.alibaba.fastjson.JSONArray;
import com.dempe.poplar.common.anno.Controller;
import com.dempe.poplar.common.anno.Param;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/26
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ConfController {

    private ConfService confService = new ConfService();

    public String listServices() throws Exception {
        return JSONArray.toJSONString(confService.listServices());
    }

    public String listServers(@Param String serviceName) throws Exception {
        return JSONArray.toJSONString(confService.listServers(serviceName));
    }

    public String getServerConf(@Param String serviceName, @Param String serverName) throws Exception {
        return confService.getServerConf(serviceName, serverName);
    }
}
