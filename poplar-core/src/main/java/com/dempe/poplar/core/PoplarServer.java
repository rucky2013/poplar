package com.dempe.poplar.core;

import com.dempe.poplar.common.Constants;
import com.dempe.poplar.core.handler.ServerHandler;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/10
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class PoplarServer {

    private final static Logger LOGGER = Logger.getLogger(PoplarServer.class);

    private PoplarBuilder build;

    private ExecutorService boss;

    private ExecutorService worker;

    private Channel channel;

    public PoplarServer(PoplarBuilder build) {
        this.build = build;
    }

    private ServerBootstrap bootstrap;

    private  PoplarContext context;


    public void startUp() throws ClassNotFoundException {
        this.boss = Executors.newFixedThreadPool(build.getBossThreadNum());
        this.worker = Executors.newFixedThreadPool(build.getWorkThreadNum());
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(this.boss, this.worker));
        bootstrap.setPipelineFactory(new ServerPipelineFactory());
        bootstrap.setOption(Constants.CHILD_KEEP_ALIVE, false);
        bootstrap.setOption(Constants.CHILD_TCP_NO_DELAY, false);
        bootstrap.setOption(Constants.REUSER_ADDRESS, false);

        channel = bootstrap.bind(new InetSocketAddress(build.getHost(), build.getPort()));
        LOGGER.info("[SERVER START] : " + build.getNodeName() + " start on /" + build.getHost() + ":" + build.getPort());

        context =new PoplarContext();
        context.initMapper();

    }


    private class ServerPipelineFactory implements ChannelPipelineFactory {
        public ChannelPipeline getPipeline() {
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
            pipeline.addLast("handler", new ServerHandler(context));
            return pipeline;
        }
    }


}
