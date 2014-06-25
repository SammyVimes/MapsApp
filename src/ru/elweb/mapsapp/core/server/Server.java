package ru.elweb.mapsapp.core.server;

import ru.elweb.mapsapp.core.map.EltechMap;

/**
 * Abstract server
 */
public interface Server {

    /**
     * Start the server
     */
	void runServer();

    /**
     * Set the map that will be used for searching by this server
     * @param map
     */
    void setMap(final EltechMap map);
	
}
