package org.jschool.multithreading;

import java.util.*;

public class FixedThreadPool implements ThreadPool {
    private final int numberOfThreads;
    private final LinkedList<Runnable> taskList = new LinkedList<>();


    public FixedThreadPool() {
        this(10);
    }

    public FixedThreadPool(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void start() {

        Thread daemonThread = new Thread(this::daemonTask);
        daemonThread.setDaemon(true);
        daemonThread.start();

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(this::providerTask);
            thread.start();
        }
    }

    @Override
    public void execute(Runnable task) {
        synchronized (taskList) {
            taskList.add(task);
        }
    }

    private void daemonTask() {
        while (true) {
            synchronized (this) {
                if (taskList.size() == 0) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    notify();
                }
            }
        }
    }

    private void providerTask() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Runnable tempTask;
            synchronized (taskList) {
                tempTask = taskList.poll();
            }
            if (tempTask != null) {
                tempTask.run();
            }
        }
    }
}

