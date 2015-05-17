package com.dempe.poplar;

import com.dempe.poplar.core.proto.example.HelloProto;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static org.jboss.netty.channel.Channels.pipeline;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/5/17
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
public class PServer {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the event pipeline factory.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline p = pipeline();
                p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
                p.addLast("protobufDecoder", new ProtobufDecoder(HelloProto.Hello.getDefaultInstance()));

                p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
                p.addLast("protobufEncoder", new ProtobufEncoder());

                p.addLast("handler", new SimpleChannelUpstreamHandler() {

                    @Override
                    public void messageReceived(
                            ChannelHandlerContext ctx, MessageEvent e) {
                        System.out.println("========");
                        HelloProto.Hello hello = (HelloProto.Hello) e.getMessage();
                        String name = hello.getName();
                        System.out.println("===name===>" + name);
                    }

                });
                return p;

            }
        });
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(8888));
    }
}
