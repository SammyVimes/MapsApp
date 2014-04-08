package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.client.Client;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.server.MapRequest;

public interface Task {

	Client getClient();
	
	MapRequest getRequest();

    EltechMap getMap();

    MapSearchAlgorithm getAlgorithm();
	
}
