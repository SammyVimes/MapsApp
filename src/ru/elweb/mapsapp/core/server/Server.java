package ru.elweb.mapsapp.core.server;

import ru.elweb.mapsapp.core.map.EltechMap;

public interface Server {

	void runServer();

    void setMap(final EltechMap map);
	
}
