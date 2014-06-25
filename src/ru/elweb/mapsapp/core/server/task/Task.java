package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.client.Client;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.server.MapRequest;

public interface Task {

    /**
     * Retrieve the client sent this task
     * @return client
     */
	Client getClient();

    /**
     * The map where we are searching
     * @return map
     */
    EltechMap getMap();

    /**
     * Algorithm using for a search
     * @return algorithm
     */
    MapSearchAlgorithm getAlgorithm();
	
}
