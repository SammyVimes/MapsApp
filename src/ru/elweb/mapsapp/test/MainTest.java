package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;

public class MainTest {
	
	public static void main(String[] args) {
        EltechMap map = TestMapBuilder.buildSimpleMap();
        Test test = new AlgorithmTest(new DijkstraAlgorithm(), map, 1, 5);
        test.run();
        (new ServerClientTest()).run();
	}
	
}
