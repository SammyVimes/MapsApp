package ru.elweb.mapsapp.core.client;

import java.net.Socket;

/**
 * Created by Semyon Danilov on 06.04.2014.
 */

/**
 * Abstract client factory
 */
public interface ClientFactory {

    /**
     * Creating a client for a socket
     *
     * @param socket client's socket
     * @return client
     */
    Client newClient(final Socket socket);

}
