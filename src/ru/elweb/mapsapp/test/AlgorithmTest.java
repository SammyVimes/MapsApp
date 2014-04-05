package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;

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
        algorithm.findPath(map, fromId, toId);
    }

}
