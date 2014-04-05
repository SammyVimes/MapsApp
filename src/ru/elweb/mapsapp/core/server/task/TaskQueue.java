package ru.elweb.mapsapp.core.server.task;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {

	private static TaskQueue instance; 
	
	private Queue<Task> queue = new LinkedList<>();
	
	
	public static TaskQueue getInstance() {
		if (instance == null) {
			instance = new TaskQueue();
		}
		return instance;
	}
	
	public synchronized void addTask(final Task task) {
		queue.add(task);
	}
	
	public synchronized Task getTask() {
		return queue.element();
	}
	
	private TaskQueue() {
		
	}
	
}
