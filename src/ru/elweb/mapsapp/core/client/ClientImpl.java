package ru.elweb.mapsapp.core.client;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.MapRequestImpl;
import ru.elweb.mapsapp.core.util.Util;
import ru.elweb.mapsapp.core.util.http.HttpQuery;
import ru.elweb.mapsapp.core.util.http.MalformedQueryException;

import java.io.IOException;
import java.io.InputStream;
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
        InputStream stream = null;
        MapRequest mapRequest = null;
        try {
            stream = clientSocket.getInputStream();
            byte buf[] = new byte[64*1024];
            int a = stream.read(buf);
            String request = new String(buf, 0, a);
            String queryStr = Util.extractQuery(request);
            HttpQuery query = HttpQuery.parse(queryStr);
            Integer fromId = Integer.valueOf(query.getRequestParam("fromID"));
            Integer toId = Integer.valueOf(query.getRequestParam("toID"));
            mapRequest = new MapRequestImpl(fromId, toId);
        } catch (IOException | MalformedQueryException e) {
            e.printStackTrace();
        }
        return mapRequest;
    }

}
