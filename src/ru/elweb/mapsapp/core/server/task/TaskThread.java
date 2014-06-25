package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.MapRequestImpl;
import ru.elweb.mapsapp.core.server.ServerException;
import ru.elweb.mapsapp.core.util.Cache;
import ru.elweb.mapsapp.core.util.Logger;

public class TaskThread extends Thread {

    private static Logger LOGGER = Logger.getLogger(TaskThread.class);

    private static Cache<MapRequest, Path> cache = new Cache<MapRequest, Path>();

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
            return;
        }
        Path path = null;
        path = getFromCache(mapRequest);
        if (path == null) {
            path = task.getAlgorithm().findPath(task.getMap(), mapRequest.getFromID(), mapRequest.getToID());
        } else {
            LOGGER.log("Got path from cache. Yay!");
        }
        if (path != null) {
            putToCache(mapRequest, path);
            deliver(path);
        } else {
            LOGGER.log("Path was not found");
        }
	}
	
	public boolean deliver(final Path path) {
		return task.getClient().sendData(path);
	}

    private static void putToCache(final MapRequest mapRequest, final Path path) {
        synchronized (cache) {
            cache.put(mapRequest, path);
        }
    }

    private static Path getFromCache(final MapRequest mapRequest) {
        synchronized (cache) {
            return cache.get(mapRequest);
        }
    }

	
}
