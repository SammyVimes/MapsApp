package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;

/**
 * Created by Администратор on 05.04.2014.
 */
public class AlgorithmTest implements Test {

    private MapSearchAlgorithm algorithm;
    private EltechMap map;

    public AlgorithmTest(final MapSearchAlgorithm algorithm, final EltechMap map) {
        this.algorithm = algorithm;
        this.map = map;
    }

    @Override
    public void run() {

    }
    
}
