package ru.elweb.mapsapp.core.server.task;

/**
 * Created by Semyon Danilov on 08.04.2014.
 */
public class TaskThreadRunner extends Thread {

    public TaskThreadRunner() {
        setDaemon(true);
    }

    @Override
    public void run() {
        TaskQueue taskQueue = TaskQueue.getInstance();
        Object monitor = taskQueue.getMonitor();
        while (true) {
            synchronized (monitor) {
                while (taskQueue.isEmpty()) {
                    try {
                        monitor.wait();
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
