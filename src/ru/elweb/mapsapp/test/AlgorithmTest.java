package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.map.node.MapNode;
import ru.elweb.mapsapp.core.util.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Created by  Semyon Danilov on 05.04.2014.
 */
public class AlgorithmTest implements Test {

    private static Logger LOGGER = Logger.getLogger(AlgorithmTest.class);

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
        LOGGER.log("Finding path from " + fromId + " to " + toId);
        for (final MapNode node : nodes) {
            LOGGER.log("Node: " + node.getId());
        }
    }

}
