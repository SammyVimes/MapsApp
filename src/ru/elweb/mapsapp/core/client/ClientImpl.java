package ru.elweb.mapsapp.core.client;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;

import java.net.Socket;

public class ClientImpl implements Client {

    private Socket clientSocket;

    public ClientImpl(final Socket socket) {
        this.clientSocket = socket;
    }

	@Override
	public boolean sendData(Path path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendInfo(String info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MapRequest getRequest() {
		return null;
	}

}
