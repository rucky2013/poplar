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
        NodeDiscovery discovery = new NodeDiscovery("/discovery/test");
        discovery.addNode("a","");

        discovery.addNode("b","testb");
        discovery.listNodes();
        discovery.destory();
    }
}
