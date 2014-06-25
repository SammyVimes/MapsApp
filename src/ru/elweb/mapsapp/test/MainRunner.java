package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.util.Logger;
import ru.elweb.mapsapp.test.graphics.GraphFrameTest;

public class MainRunner {

    private static Logger LOGGER = Logger.getLogger(MainRunner.class);
	
	public static void main(String[] args) {
        EltechMap map = TestMapBuilder.buildSimpleMap();
        Test test = new AlgorithmTest(new DijkstraAlgorithm(), map, 1, 5);
        LOGGER.log("******** Starting algorithm test ********");
        test.run();
        LOGGER.log("******** Starting server&client test ********");
        (new ServerClientTest()).run();
        (new GraphFrameTest()).run();
	}
	
}
