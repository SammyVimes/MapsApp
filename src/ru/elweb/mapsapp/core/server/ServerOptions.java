package ru.elweb.mapsapp.core.server;

/**
 * Created by Semyon Danilov on 06.04.2014.
 */
public class ServerOptions {

    protected String host = null;
    protected int port = -1;

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
