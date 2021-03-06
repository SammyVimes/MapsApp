package ru.elweb.mapsapp.core.server;

import ru.elweb.mapsapp.core.client.Client;
import ru.elweb.mapsapp.core.client.ClientFactory;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.server.task.Task;
import ru.elweb.mapsapp.core.server.task.TaskImpl;
import ru.elweb.mapsapp.core.server.task.TaskQueue;
import ru.elweb.mapsapp.core.server.task.TaskThreadRunner;
import ru.elweb.mapsapp.core.util.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImpl implements Server {

    private Logger LOGGER = Logger.getLogger(ServerImpl.class);

    private ClientFactory clientFactory;
    private ServerOptions serverOptions;

    private EltechMap map;

    public ServerImpl(final ServerOptions serverOptions, final ClientFactory clientFactory) {
        this.serverOptions = serverOptions;
        this.clientFactory = clientFactory;
    }

    @Override
    public void runServer() {
        Thread serverThread = new ServerThread();
        serverThread.start();
    }

    @Override
    public void setMap(final EltechMap map) {
        this.map = map;
    }

    private final class ServerThread extends Thread {

        private static final String TAG = "ServerThread";
        private static final int DEFAULT_MAX_CONNECTIONS = 40;
        private final int maxConnections;

        private boolean isAcceptingClients = true;

        private ServerSocket serverSocket = null;

        public ServerThread(final int maxConnections) {
            this.maxConnections = maxConnections;
            setDaemon(true);
        }

        public ServerThread() {
            this.maxConnections = DEFAULT_MAX_CONNECTIONS;
        }

        @Override
        public void run() {
            LOGGER.log("Creating server socket, options = " + serverOptions.toString());
            try {
                serverSocket = new ServerSocket(serverOptions.port, 0, InetAddress.getByName(serverOptions.host));
            } catch (IOException e) {
                LOGGER.error("Cannot create server socket: " + e.getMessage());
            }
            if (serverSocket == null) {
                return;
            }
            TaskThreadRunner threadRunner = new TaskThreadRunner();
            threadRunner.start();
            LOGGER.log("Server started");
            while (isAcceptingClients) {
                try {
                    Socket socket = serverSocket.accept();
                    Client client = clientFactory.newClient(socket);
                    Task task = new TaskImpl(client, map, serverOptions.algorithm);
                    TaskQueue.getInstance().addTask(task);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER.log("Server stopped");
        }

    }

}
