package ru.elweb.mapsapp.core.client;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.MapRequestImpl;
import ru.elweb.mapsapp.core.server.ServerException;
import ru.elweb.mapsapp.core.util.Util;
import ru.elweb.mapsapp.core.util.http.HttpQuery;
import ru.elweb.mapsapp.core.util.http.MalformedQueryException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientImpl implements Client {

    private Socket clientSocket;

    public ClientImpl(final Socket socket) {
        this.clientSocket = socket;
    }

	@Override
	public boolean sendData(Path path) {
        OutputStream stream = null;
        try {
            stream = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        String pathString = path.toString();
        try {
            stream.write("HTTP/1.1 200 OK\\r\\n".getBytes());
            byte[] response = pathString.getBytes();
            int len = response.length;
            stream.write(String.valueOf("Content-Length: " + len + "\r\n").getBytes());
            stream.write("Content-Type: text/html\r\n\r\n\r\n".getBytes());
            stream.write(response);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
	}

	@Override
	public boolean sendInfo(String info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MapRequest getRequest() throws ServerException {
        InputStream stream = null;
        MapRequest mapRequest = null;
        try {
            stream = clientSocket.getInputStream();
            byte buf[] = new byte[64*1024];
            int a = stream.read(buf);
            if (a == -1) {
                throw new ServerException("Can't read clients request");
            }
            String request = new String(buf, 0, a);
            String queryStr = Util.extractQuery(request);
            HttpQuery query = HttpQuery.parse(queryStr);
            Integer fromId = Integer.valueOf(query.getRequestParam("fromID"));
            Integer toId = Integer.valueOf(query.getRequestParam("toID"));
            mapRequest = new MapRequestImpl(fromId, toId);
        } catch (IOException | MalformedQueryException e) {
            throw new ServerException(e.getMessage());
        }
        return mapRequest;
    }

}
