package ru.elweb.mapsapp.core.algorithm.dijkstra;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.annotation.MethodDescription;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.map.node.Branch;
import ru.elweb.mapsapp.core.map.node.DijkstraMapNode;
import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.List;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */
public class DijkstraAlgorithm implements MapSearchAlgorithm {

    //TODO: so we store our path. This means that if we get to dead end we could always walk back EXACTLY
    //TODO: the same way we got here. This is good: just walk back and see if prev nodes has unflagged branch nodes
    @Override
    @MethodDescription(link = "http://ru.wikipedia.org/wiki/%C0%EB%E3%EE%F0%E8%F2%EC_%C4%E5%E9%EA%F1%F2%F0%FB")
    public Path findPath(final EltechMap map, final int fromId, final int toId) {
        Long startTime = System.nanoTime();
        DijkstraMapNode currentNode = (DijkstraMapNode) map.getNodeById(fromId);
        DijkstraMapNode startNode = currentNode;
        Path currentPath = new Path();
        Path returnPath = new Path();
        currentNode.getDijkstraData().pathLen = 0;
        DijkstraMapNode finalNode = null;
        while (true) {
            if (currentNode == null) {
                break;
            }
            List<Branch> branchList = currentNode.getBranches();
            DijkstraData currentNodeData = currentNode.getDijkstraData();
            long currentNodePathLen = currentNodeData.pathLen;
            long min = -1;
            DijkstraMapNode nextCurrentNode = null; //this node should have smallest path
            for (Branch branch : branchList) {
                DijkstraMapNode node = (DijkstraMapNode) branch.getNode();
                DijkstraData dijkstraData = node.getDijkstraData();
                if (dijkstraData.flag) {
                    continue;
                }
                long nodePathLen = dijkstraData.pathLen;
                if (nodePathLen == -1 || nodePathLen > currentNodePathLen + branch.getLength()) {
                    dijkstraData.pathLen = currentNodePathLen + branch.getLength();
                }
                if (min == -1 || min > dijkstraData.pathLen) {
                    nextCurrentNode = node;
                    min = dijkstraData.pathLen;
                }
            }
            currentNodeData.flag = true;
            currentPath.addNode(currentNode);
            if (currentNode.getId() == toId) { //current node is desired and also is finished
                finalNode = currentNode;
            }
            MapNode tmpNode = currentNode;
            currentNode = nextCurrentNode;
            if (currentNode == null) {
                List<MapNode> nodes = currentPath.getNodes();
                for (int i = nodes.size() - 1; i >= 0; i--) {
                    MapNode node = nodes.remove(i);
                    List<Branch> branches = node.getBranches();
                    for (Branch branch : branches) {
                        DijkstraMapNode anotherNode = (DijkstraMapNode) branch.getNode();
                        if (!anotherNode.getDijkstraData().flag) {
                            currentNode = (DijkstraMapNode) node;
                            break;
                        }
                    }
                    if (currentNode != null) {
                        break;
                    }
                }
            }
        }

        //now forming a path from an end to beginning
        if (finalNode == null) {
            return null;
        }
        currentNode = finalNode;
        returnPath.addNode(finalNode);
        while (currentNode.getId() != fromId) {
            List<Branch> branches = currentNode.getBranches();
            long curPathLen = currentNode.getDijkstraData().pathLen;
            for (Branch branch : branches) {
                DijkstraMapNode node = (DijkstraMapNode) branch.getNode();
                DijkstraData data = node.getDijkstraData();
                if (curPathLen - branch.getLength() == data.pathLen) {
                    currentNode = node;
                    break;
                }
            }
            returnPath.addNode(currentNode);
        }
        for (MapNode node : map.getNodes()) {
            DijkstraMapNode dijkstraMapNode = (DijkstraMapNode) node;
            dijkstraMapNode.removeDijkstraData();
        }
        Long endTime = System.nanoTime();
        System.out.println("Path finding time: " + (endTime - startTime) + " ns");
        return returnPath;
    }

}
