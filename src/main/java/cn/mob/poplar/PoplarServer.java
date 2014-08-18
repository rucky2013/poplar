package cn.mob.poplar;

import cn.mob.poplar.core.HttpServer;
import cn.mob.poplar.core.RequestMapping;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0 date: 2014/8/15
 * @author: Dempe
 */
@Component
public class PoplarServer {

    private static final Logger LOGGER = Logger.getLogger(PoplarServer.class);
    private static final String DEF_PORT = "8899";

    @Resource
    private RequestMapping registry;

    public void startUp() {
        String portStr = Conf.getString(R.poplar_port, DEF_PORT);
        int port = Integer.parseInt(portStr);

        HttpServer server = new HttpServer(registry, R.poplar_host, port);
        server.childKeepAlive().childTcpNoDelay().closeOnJvmShutdown().reuserAddress().startup();

    }



}
