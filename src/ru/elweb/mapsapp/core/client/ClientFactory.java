package ru.elweb.mapsapp.core.client;

import java.net.Socket;

/**
 * Created by Semyon Danilov on 06.04.2014.
 */
public interface ClientFactory {

    Client newClient(final Socket socket);

}
