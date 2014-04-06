package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.client.Client;
import ru.elweb.mapsapp.core.server.MapRequest;

public interface Task {

    boolean extractRequest();

	Client getClient();
	
	MapRequest getRequest();
	
}
