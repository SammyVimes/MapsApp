package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.client.Client;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.server.MapRequest;

public class TaskImpl implements Task {

	private Client client;

    private EltechMap map;

    private MapSearchAlgorithm algorithm;
	
	public TaskImpl(final Client client, final EltechMap map, final MapSearchAlgorithm algorithm) {
		this.client = client;
        this.map = map;
        this.algorithm = algorithm;
	}

	@Override
	public Client getClient() {
		return client;
	}

    @Override
    public EltechMap getMap() {
        return map;
    }

    @Override
    public MapSearchAlgorithm getAlgorithm() {
        return algorithm;
    }

}
