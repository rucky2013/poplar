package com.dempe.poplar.core.node;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/4
 * Time: 上午8:38
 * To change this template use File | Settings | File Templates.
 */
public class NodeDiscovery {


    private final static Logger LOGGER = Logger.getLogger(NodeDiscovery.class);

    CuratorFramework client = null;
    private String path;
    Map<String, ServiceProvider<Node>> providers = Maps.newConcurrentMap();
    ServiceDiscovery<Node> serviceDiscovery = null;
    List<NodeServer> servers = Lists.newArrayList();

    public NodeDiscovery(String path) {
        this.path = path;
        init();
    }

    public void init() {

        client = ZkClient.getClient();
        client.start();

        JsonInstanceSerializer<Node> serializer = new JsonInstanceSerializer<Node>(Node.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(Node.class).client(client).basePath(path).serializer(serializer).build();
        try {
            serviceDiscovery.start();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    public void destory() {
        for (ServiceProvider<Node> cache : providers.values()) {
            CloseableUtils.closeQuietly(cache);
        }

        CloseableUtils.closeQuietly(serviceDiscovery);
        CloseableUtils.closeQuietly(client);
    }


    public Node randomNode(String name) throws Exception {
        // this shows how to use a ServiceProvider
        // in a real application you'd create the ServiceProvider early for the service(s) you're interested in

        Node node =null;
        ServiceProvider<Node> provider = providers.get(name);
        if (provider == null) {
            provider = serviceDiscovery.serviceProviderBuilder().serviceName(name).providerStrategy(new RandomStrategy<Node>()).build();
            providers.put(name, provider);
            provider.start();

            Thread.sleep(2500); // give the provider time to warm up - in a real application you wouldn't need to do this
        }

        ServiceInstance<Node> serviceInstance = provider.getInstance();
        if (serviceInstance == null) {
            System.err.println("No instances named: " + name);
        } else {
            node = serviceInstance.getPayload();
        }
        return node;

    }

    public void listNodes() throws Exception {
        // This shows how to query all the instances in service discovery

        try {
            Collection<String> serviceNames = serviceDiscovery.queryForNames();
            System.out.println(serviceNames.size() + " type(s)");
            for (String serviceName : serviceNames) {
                Collection<ServiceInstance<Node>> instances = serviceDiscovery.queryForInstances(serviceName);
                System.out.println(serviceName);
                for (ServiceInstance<Node> instance : instances) {
                    System.out.println("node==>"+instance.getPayload());
                }
            }
        } finally {
            CloseableUtils.closeQuietly(serviceDiscovery);
        }
    }


    public void deleteNode(final String name) {
        // simulate a random instance going down
        // in a real application, this would occur due to normal operation, a crash, maintenance, etc.

        NodeServer server = Iterables.find
                (
                        servers,
                        new Predicate<NodeServer>() {
                            @Override
                            public boolean apply(NodeServer server) {
                                return server.getThisInstance().getName().endsWith(name);
                            }
                        },
                        null
                );
        if (server == null) {
            System.err.println("No servers found named: " + name);
            return;
        }

        servers.remove(server);
        CloseableUtils.closeQuietly(server);
        System.out.println("Removed a random instance of: " + name);
    }

    public void addNode(String name, String desc) throws Exception {

        NodeServer server = new NodeServer(client, path, name, desc.toString());
        servers.add(server);
        server.start();
        System.out.println(name + " added");
    }


}
