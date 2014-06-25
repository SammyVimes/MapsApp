package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.Factory;
import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraFactory;
import ru.elweb.mapsapp.core.annotation.TestImage;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.node.Branch;
import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class TestMapBuilder {

    static Factory factory = new DijkstraFactory();

    static EltechMap newEltechMap() {
        return null;
    }

    @TestImage("http://upload.wikimedia.org/wikipedia/commons/5/55/Dijkstra_graph3.PNG")
    public static EltechMap buildSimpleMap() {
        EltechMap map = new EltechMap();
        MapNode node1 = factory.newMapNode(1, false);
        MapNode node2 = factory.newMapNode(2, false);
        MapNode node3 = factory.newMapNode(3, false);
        MapNode node4 = factory.newMapNode(4, false);
        MapNode node5 = factory.newMapNode(5, false);
        MapNode node6 = factory.newMapNode(6, false);
        Branch b12 = new Branch(7, node2);
        Branch b16 = new Branch(14, node6);
        Branch b13 = new Branch(9, node3);
        node1.addBranch(b12);
        node1.addBranch(b16);
        node1.addBranch(b13);
        Branch b21 = new Branch(7, node1);
        Branch b23 = new Branch(7, node3);
        Branch b24 = new Branch(15, node4);
        node2.addBranch(b21);
        node2.addBranch(b23);
        node2.addBranch(b24);
        Branch b31 = new Branch(9, node1);
        Branch b32 = new Branch(7, node2);
        Branch b34 = new Branch(11, node4);
        Branch b36 = new Branch(2, node6);
        node3.addBranch(b31);
        node3.addBranch(b32);
        node3.addBranch(b34);
        node3.addBranch(b36);
        Branch b42 = new Branch(11, node2);
        Branch b43 = new Branch(11, node3);
        Branch b45 = new Branch(6, node5);
        node4.addBranch(b42);
        node4.addBranch(b43);
        node4.addBranch(b45);
        Branch b54 = new Branch(6, node4);
        Branch b56 = new Branch(9, node6);
        node5.addBranch(b54);
        node5.addBranch(b56);
        Branch b61 = new Branch(14, node1);
        Branch b63 = new Branch(2, node3);
        Branch b65 = new Branch(9, node5);
        node6.addBranch(b61);
        node6.addBranch(b63);
        node6.addBranch(b65);
        map.addNode(node1);
        map.addNode(node2);
        map.addNode(node3);
        map.addNode(node4);
        map.addNode(node5);
        map.addNode(node6);
        return map;
    }

    public static EltechMap buildHardMap() {
        EltechMap map = new EltechMap();
        Random random = new Random();
        List<MapNode> nodes = new LinkedList<>();
        int n = 100;
        for (int i = 0; i < n; i++) {
            MapNode node = factory.newMapNode(i, false);
            nodes.add(node);
            map.addNode(node);
        }
        for (int i = 0; i < 2 * n; i++) {
            int a = (int) (random.nextFloat() * n);
            int b = (int) (random.nextFloat() * n);
            if (nodes.size() <= a || nodes.size() <= b) {
                continue;
            }
            MapNode aNode = nodes.get(a);
            MapNode bNode = nodes.get(b);
            boolean shouldSkip = false;
            List<Branch> branchesOfA = aNode.getBranches();
            for (Branch branch : branchesOfA) {
                if (branch.getNode() == bNode) {
                    shouldSkip = true;
                    break;
                }
            }
            if (a == b || shouldSkip) {
                continue;
            }
            int randLength = (int) (random.nextFloat() * 20 + 1);
            Branch aToB = new Branch(randLength, bNode);
            Branch bToA = new Branch(randLength, aNode);
            aNode.addBranch(aToB);
            bNode.addBranch(bToA);
        }
        for (MapNode node : nodes) {
            int branchQuantity = node.getBranches().size();
            if (branchQuantity < 2) {
                node.setDEAD_END(true);
            }
        }
        return map;
    }

}
