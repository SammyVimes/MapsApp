package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.test.graphics.GraphFrameTest;

public class MainRunner {
	
	public static void main(String[] args) {
        EltechMap map = TestMapBuilder.buildSimpleMap();
        Test test = new AlgorithmTest(new DijkstraAlgorithm(), map, 1, 5);
        test.run();
        (new ServerClientTest()).run();
        //(new GraphFrameTest()).run();
	}
	
}
