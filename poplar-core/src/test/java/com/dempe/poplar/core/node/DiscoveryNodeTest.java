package com.dempe.poplar.core.node;

/**
 * Created with IntelliJ IDEA.
 * User: zhengdaxia
 * Date: 15/4/4
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
public class DiscoveryNodeTest {

    public static void main(String[] args) throws Exception {
        NodeDiscovery discovery = new NodeDiscovery("/discovery/test3");
        discovery.addNode("a3","");

        discovery.addNode("b3","testb");
        discovery.listNodes();

        discovery.deleteNode("a3");

        discovery.listNodes();
        discovery.destory();
    }
}
