package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraAlgorithm;
import ru.elweb.mapsapp.core.client.ClientFactoryImpl;
import ru.elweb.mapsapp.core.server.Server;
import ru.elweb.mapsapp.core.server.ServerImpl;
import ru.elweb.mapsapp.core.server.ServerOptions;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Semyon Danilov on 08.04.2014.
 */
public class ServerClientTest implements Test {

    @Override
    public void run() {
        ServerOptions options = new ServerOptions();
        options.setHost("localhost");
        options.setPort(8080);
        options.setAlgorithm(new DijkstraAlgorithm());
        Server server = new ServerImpl(options, new ClientFactoryImpl());
        server.setMap(TestMapBuilder.buildSimpleMap());
        server.runServer();

        (new Thread() {

            @Override
            public void run() {
                try {
                    URLConnection connection = new URL("http://localhost:8080/?foobar=sos").openConnection();
                    connection.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

}
