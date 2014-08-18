package cn.mob.poplar.core;


import cn.mob.poplar.R;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2014/8/6.
 */
public class HttpServer<HttpServerT extends HttpServer<HttpServerT>> {

    private static final Logger LOGGER = Logger.getLogger(HttpServer.class);

    private static final String TRUE = "true";
    private static final String FALSE = "false";


    protected Properties props = new Properties();

    Mapping registry = new RequestMapping();

    ExecutorService worker;
    ExecutorService boss;
    ServerBootstrap bootstrap;

    Channel channel;
    String hostname;
    int port;

    protected HttpServer() {

    }

    public HttpServer(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public HttpServer(Mapping registry, String hostname, int port) {
        this.registry = registry;
        this.hostname = hostname;
        this.port = port;

    }

    public void startup() {
        this.boss = Executors.newFixedThreadPool(4);
        this.worker = Executors.newFixedThreadPool(32);
        this.bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(this.boss, this.worker));
        bootstrap.setPipelineFactory(new ServerPipelineFactory());
        bootstrap.setOption(R.childKeepAlive, props.getProperty(R.childKeepAlive, FALSE));
        bootstrap.setOption(R.childTcpNoDelay, props.getProperty(R.childTcpNoDelay, FALSE));
        bootstrap.setOption(R.reuserAddress, props.getProperty(R.reuserAddress, FALSE));
        channel = bootstrap.bind(new InetSocketAddress(hostname, port));
        LOGGER.info("Server start on /" + hostname + ":" + port);
    }

    public void close() {
        this.boss.shutdown();
        this.worker.shutdown();
        if (this.channel != null) {
            this.channel.close().addListener(ChannelFutureListener.CLOSE);
        }
        System.exit(0);
    }

    public boolean isClosed() {
        return false;
    }

    public HttpServer closeOnJvmShutdown() {
        return new CloseOnJVMShutdown(registry, hostname, port);
    }

    public HttpServerT childTcpNoDelay() {
        props.setProperty(R.childTcpNoDelay, TRUE);
        return getThis();
    }

    public HttpServerT reuserAddress() {
        props.setProperty(R.reuserAddress, TRUE);
        return getThis();
    }

    public HttpServerT childKeepAlive() {
        props.setProperty(R.childKeepAlive, TRUE);
        return getThis();
    }

    protected HttpServerT getThis() {
        return (HttpServerT) this;
    }

    public static class CloseOnJVMShutdown extends HttpServer {

        final protected AtomicBoolean shutdownHappened = new AtomicBoolean(false);
        final Runnable hookRunnable = new Runnable() {
            @Override
            public void run() {
                shutdownHappened.set(true);
                CloseOnJVMShutdown.this.hook = null;
                if (CloseOnJVMShutdown.this.isClosed())
                    return;
                CloseOnJVMShutdown.this.close();
            }
        };
        Thread hook;

        public CloseOnJVMShutdown(Mapping registry, String hostname, int port) {
            this.registry = registry;
            this.hostname = hostname;
            this.port = port;
            hook = new Thread(hookRunnable, "HttpServer shutdown hook");
            Runtime.getRuntime().addShutdownHook(hook);
        }

        public void close() {
            super.close();
            if (!shutdownHappened.get() && hook != null) {
                Runtime.getRuntime().removeShutdownHook(hook);
            }
            hook = null;

        }
    }

    private class ServerPipelineFactory implements ChannelPipelineFactory {
        public ChannelPipeline getPipeline() {
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
            pipeline.addLast("handler", new ServerHandler(registry, worker));
            return pipeline;
        }
    }

}
