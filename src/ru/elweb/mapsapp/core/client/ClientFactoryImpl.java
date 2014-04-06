package ru.elweb.mapsapp.core.client;

import java.net.Socket;

/**
 * Created by Semyon Danilov on 06.04.2014.
 */
public class ClientFactoryImpl implements ClientFactory {

    @Override
    public Client newClient(final Socket socket) {
        return new ClientImpl(socket);
    }

}
