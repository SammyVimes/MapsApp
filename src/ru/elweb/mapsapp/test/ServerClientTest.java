package ru.elweb.mapsapp.test;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraAlgorithm;
import ru.elweb.mapsapp.core.client.ClientFactoryImpl;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.server.Server;
import ru.elweb.mapsapp.core.server.ServerImpl;
import ru.elweb.mapsapp.core.server.ServerOptions;
import ru.elweb.mapsapp.core.util.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Semyon Danilov on 08.04.2014.
 */
public class ServerClientTest implements Test {

    private static final Logger LOGGER = Logger.getLogger(ServerClientTest.class);

    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void run() {
        ServerOptions options = new ServerOptions();
        options.setHost("localhost");
        options.setPort(8080);
        options.setAlgorithm(new DijkstraAlgorithm());
        Server server = new ServerImpl(options, new ClientFactoryImpl());
        EltechMap map = TestMapBuilder.buildHardMap();
        LOGGER.log("Generated map with " + map.getNodes().size() + " nodes. Type in your browser: http://localhost:8080/?fromId={From where to search}&toId={Search For}");
        LOGGER.log("e.g. http://localhost:8080/?fromID=25&toID=52");
        LOGGER.log("In this test I create a client manually to find a way from 25 to 52 and receive the path, see output in console in 2 seconds");
        server.setMap(map);
        server.runServer();
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/?fromID=25&toID=52").openConnection();
                    connection.setDoOutput(false);
                    connection.setDoInput(true);
                    InputStream is = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(reader);
                    LOGGER.log("Got result: ");
                    String resultString;
                    while ((resultString = in.readLine()) != null) {
                        LOGGER.log(resultString);
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 2, TimeUnit.SECONDS);

    }

}
