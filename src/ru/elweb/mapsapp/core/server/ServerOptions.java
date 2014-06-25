package ru.elweb.mapsapp.core.server;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;

/**
 * Created by Semyon Danilov on 06.04.2014.
 */
public class ServerOptions {

    protected String host = null;
    protected int port = -1;
    protected MapSearchAlgorithm algorithm;


    public void setHost(final String host) {
        this.host = host;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public void setAlgorithm(final MapSearchAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
