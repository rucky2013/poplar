package com.dempe.poplar.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.Closeable;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/4
 * Time: 上午8:48
 * To change this template use File | Settings | File Templates.
 */
public class NodeServer implements Closeable {

    private final ServiceDiscovery<Node> serviceDiscovery;
    private final ServiceInstance<Node> thisInstance;

    public NodeServer(CuratorFramework client, String path, String serviceName, String description) throws Exception {
        // in a real application, you'd have a convention of some kind for the URI layout
        UriSpec uriSpec = new UriSpec("{scheme}://foo.com:{port}");

        thisInstance = ServiceInstance.<Node>builder()
                .name(serviceName)
                .payload(new Node("localhost", 8080))
                .port((int) (65535 * Math.random())) // in a real application, you'd use a common port
                .uriSpec(uriSpec)
                .build();

        // if you mark your payload class with @JsonRootName the provided JsonInstanceSerializer will work
        JsonInstanceSerializer<Node> serializer = new JsonInstanceSerializer<Node>(Node.class);

        serviceDiscovery = ServiceDiscoveryBuilder.builder(Node.class)
                .client(client)
                .basePath(path)
                .serializer(serializer)
                .thisInstance(thisInstance)
                .build();
    }

    public ServiceInstance<Node> getThisInstance() {
        return thisInstance;
    }

    public void start() throws Exception {
        serviceDiscovery.start();
    }

    @Override
    public void close() throws IOException {
        CloseableUtils.closeQuietly(serviceDiscovery);
    }
}
