package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.client.Client;
import ru.elweb.mapsapp.core.server.MapRequest;

public class TaskImpl implements Task {

	private Client client;
	private MapRequest mapRequest;
	
	public TaskImpl(final Client client) {
		this.client = client;
	}

    public boolean extractRequest() {
        return false;
    }

	@Override
	public Client getClient() {
		return client;
	}

	@Override
	public MapRequest getRequest() {
		return mapRequest;
	}
	
}
