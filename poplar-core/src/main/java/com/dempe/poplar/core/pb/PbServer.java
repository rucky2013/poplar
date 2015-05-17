package com.dempe.poplar.core.pb;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/27
 * Time: 下午11:09
 * To change this template use File | Settings | File Templates.
 */
public class PbServer {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newFixedThreadPool(4),
                Executors.newFixedThreadPool(4)));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return null;
            }
        });
        bootstrap.bind();

    }
}
