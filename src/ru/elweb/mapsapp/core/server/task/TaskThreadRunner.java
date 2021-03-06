package ru.elweb.mapsapp.core.server.task;

/**
 * Created by Semyon Danilov on 08.04.2014.
 */

/**
 * Simply executing task when have one got from a TaskQueue
 */
public class TaskThreadRunner extends Thread {

    public TaskThreadRunner() {
        setDaemon(true);
    }

    @Override
    public void run() {
        TaskQueue taskQueue = TaskQueue.getInstance();
        while (true) {
            synchronized (taskQueue) {
                while (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Task task = taskQueue.getTask();
            TaskThread taskThread = new TaskThread(task);
            taskThread.start();
        }
    }
}
