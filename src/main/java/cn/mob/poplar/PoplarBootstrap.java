package cn.mob.poplar;

import cn.mob.poplar.core.HttpServer;
import cn.mob.poplar.core.RequestRegistry;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2014/8/6.
 */
@Component
public class PoplarBootstrap {

    private static final Logger LOGGER = Logger.getLogger(PoplarBootstrap.class);
    private static final String DEF_PORT = "8899";

    public void startUp() {
        String portStr = Conf.getString(R.poplar_port, DEF_PORT);
        int port = Integer.parseInt(portStr);
        RequestRegistry registry = new RequestRegistry();

        HttpServer server = new HttpServer(registry, R.poplar_host, port);
        server.childKeepAlive().childTcpNoDelay().closeOnJvmShutdown().reuserAddress().startup();

    }


}
