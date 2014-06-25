package ru.elweb.mapsapp.core.server.task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Synchronized queue of tasks. Synchronized with locks because I like locks
 */
public class TaskQueue {

	private static TaskQueue instance;
	
	private Queue<Task> queue = new LinkedList<>();

    private Lock lock = new ReentrantLock();

    public boolean isEmpty() {
        lock.lock();
        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

	public static TaskQueue getInstance() {
		if (instance == null) {
			instance = new TaskQueue();
		}
		return instance;
	}
	
	public void addTask(final Task task) {
        lock.lock();
        try {
            queue.add(task);
        } finally {
            lock.unlock();
        }
        synchronized (this) {
            notifyAll();
        }
	}
	
	public Task getTask() {
        lock.lock();
        try {
            return queue.remove();
        } finally {
            lock.unlock();
        }
	}
	
	private TaskQueue() {

	}
	
}
