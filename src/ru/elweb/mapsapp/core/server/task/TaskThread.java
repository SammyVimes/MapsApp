package ru.elweb.mapsapp.core.server.task;

import ru.elweb.mapsapp.core.map.Path;

public class TaskThread extends Thread {

	private Task task;
	
	@Override
	public void run() {
		
	}
	
	public boolean deliver(final Path path) {
		//TODO: impl
		return false;
	}
	
}
