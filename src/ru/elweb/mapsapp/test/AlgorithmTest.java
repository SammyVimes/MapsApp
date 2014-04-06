package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.Collections;
import java.util.List;

/**
 * Created by  Semyon Danilov on 05.04.2014.
 */
public class AlgorithmTest implements Test {

    private MapSearchAlgorithm algorithm;
    private EltechMap map;

    private int fromId;
    private int toId;

    public AlgorithmTest(final MapSearchAlgorithm algorithm, final EltechMap map, final int fromId, final int toId) {
        this.algorithm = algorithm;
        this.map = map;
        this.fromId = fromId;
        this.toId = toId;
    }

    @Override
    public void run() {
        Path path = algorithm.findPath(map, fromId, toId);
        List<MapNode> nodes = path.getNodes();
        Collections.reverse(path.getNodes());
        System.out.println("Finding path from " + fromId + " to " + toId);
        for (final MapNode node : nodes) {
            System.out.println("Node: " + node.getId());
        }
    }

}
