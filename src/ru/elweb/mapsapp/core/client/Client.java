package ru.elweb.mapsapp.core.client;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;

public interface Client {

	boolean sendData(final Path path);
	boolean sendInfo(final String info);
	MapRequest getRequest();
	
}
