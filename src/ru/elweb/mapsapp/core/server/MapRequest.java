package ru.elweb.mapsapp.core.server;

public interface MapRequest {

    /**
     * From where to search
     * @return
     */
	int getFromID();

    /**
     * Till where to search
     * @return
     */
	int getToID();
	
}
