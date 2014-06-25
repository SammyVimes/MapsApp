package ru.elweb.mapsapp.core.client;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.ServerException;

public interface Client {

    /**
     * Send path response to client
     * @param path
     * @return
     */
	boolean sendData(final Path path);

    /**
     * Send custom string to client (for example error)
     * @param info
     * @return
     */
	boolean sendInfo(final String info);

    /**
     * Extract request from clients input
     * @return MapRequest
     * @throws ServerException if query is malformed
     */
	MapRequest getRequest() throws ServerException;
	
}
