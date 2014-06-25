package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.ServerException;
import ru.elweb.mapsapp.core.util.Logger;

public class TaskThread extends Thread {

    private static Logger LOGGER = Logger.getLogger(TaskThread.class);

	private Task task;

    public TaskThread(final Task task) {
        this.task = task;
    }
	
	@Override
	public void run() {
        MapRequest mapRequest = null;
        try {
            mapRequest = task.getClient().getRequest();
        } catch (ServerException e) {
            LOGGER.log(e.getMessage());
        }
        Path path = task.getAlgorithm().findPath(task.getMap(), mapRequest.getFromID(), mapRequest.getToID());
        deliver(path);
	}
	
	public boolean deliver(final Path path) {
        if (path == null) {
            return false;
        }
		return task.getClient().sendData(path);
	}
	
}
