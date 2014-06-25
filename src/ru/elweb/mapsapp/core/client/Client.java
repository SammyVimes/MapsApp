package ru.elweb.mapsapp.core.client;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.ServerException;

public interface Client {

	boolean sendData(final Path path);
	boolean sendInfo(final String info);
	MapRequest getRequest() throws ServerException;
	
}
