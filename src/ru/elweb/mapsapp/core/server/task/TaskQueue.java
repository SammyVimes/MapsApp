package ru.elweb.mapsapp.core.server.task;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {

	private static TaskQueue instance;

    private Object monitor = new Object();
	
	private Queue<Task> queue = new LinkedList<>();

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

	public static TaskQueue getInstance() {
		if (instance == null) {
			instance = new TaskQueue();
		}
		return instance;
	}

    public Object getMonitor() {
        return monitor;
    }
	
	public synchronized void addTask(final Task task) {
		queue.add(task);
        synchronized (monitor) {
            monitor.notifyAll();
        }
	}
	
	public synchronized Task getTask() {
		return queue.remove();
	}
	
	private TaskQueue() {

	}
	
}
