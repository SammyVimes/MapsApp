package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.server.MapRequest;

public class TaskThread extends Thread {

	private Task task;

    public TaskThread(final Task task) {
        this.task = task;
    }
	
	@Override
	public void run() {
        MapRequest mapRequest = task.getClient().getRequest();
        Path path = task.getAlgorithm().findPath(task.getMap(), mapRequest.getFromID(), mapRequest.getToID());
        path.toString();
	}
	
	public boolean deliver(final Path path) {
		//TODO: impl
		return false;
	}
	
}
