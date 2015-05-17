package com.dempe.poplar;

import com.dempe.poplar.core.proto.example.HelloProto;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
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
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class PClient {

    public static void main(String[] args) {
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Configure the event pipeline factory.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline p = pipeline();
                p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
                p.addLast("protobufDecoder", new ProtobufDecoder(HelloProto.Hello.getDefaultInstance()));

                p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
                p.addLast("protobufEncoder", new ProtobufEncoder());

                //p.addLast("handler", new SimpleChannelUpstreamHandler());
                return p;
            }
        });

        // Make a new connection.
        ChannelFuture connectFuture =
                bootstrap.connect(new InetSocketAddress("127.0.0.1", 8888));

        // Wait until the connection is made successfully.
        Channel channel = connectFuture.awaitUninterruptibly().getChannel();
        HelloProto.Hello.Builder hello = HelloProto.Hello.newBuilder();
        hello.setId(1);
        hello.setName("dempe");
        hello.setEmail("yinweiniyu@163.com");
        hello.addFriends("wqq");
        channel.write(hello);



        // Wait until the connection is closed or the connection attempt fails.
        channel.getCloseFuture().awaitUninterruptibly();

        // Shut down thread pools to exit.
        bootstrap.releaseExternalResources();
    }
}
