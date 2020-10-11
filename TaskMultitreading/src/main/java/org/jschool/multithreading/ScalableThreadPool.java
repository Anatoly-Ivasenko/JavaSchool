package org.jschool.multithreading;

import java.util.*;


public class ScalableThreadPool implements ThreadPool {
    private final int minNumberOfThreads;
    private final int maxNumberOfThreads;
    private final Object locker = new Object();
    private final LinkedList<Runnable> taskList = new LinkedList<>();
    private final Set<Thread> threadSet = new HashSet<>();


    public ScalableThreadPool() {
        this(8, 16);
    }

    public ScalableThreadPool(int minNumberOfThreads, int maxNumberOfThreads) {
        this.minNumberOfThreads = minNumberOfThreads;
        this.maxNumberOfThreads = maxNumberOfThreads;
    }

    @Override
    public void start() {

        Thread daemonThread = new Thread(this::daemonTask);
        daemonThread.setDaemon(true);
        daemonThread.start();

        for (int i = 0; i < minNumberOfThreads; i++) {
            Thread thread = new Thread(this::providerTask);
            thread.start();
            synchronized (threadSet) {
                threadSet.add(thread);
            }
        }
    }

    @Override
    public void execute(Runnable task) {

        synchronized (taskList) {
            taskList.add(task);
        }

        synchronized (this) {
            if (threadSet.size() < maxNumberOfThreads) {
                boolean allRun = true;
                for (Thread thread : threadSet) {
                    if (thread.getState() == Thread.State.WAITING) {
                        allRun = false;
                        break;
                    }
                }
                if (allRun) {
                    Thread thread = new Thread(this::providerTask);
                    thread.start();
                    synchronized (threadSet) {
                        threadSet.add(thread);
                    }
                }
            }
        }
    }

    private void daemonTask() {
        while (true) {
            synchronized (this) {
                if (taskList.size() == 0) {
                    if (threadSet.size() > minNumberOfThreads) {
                        Iterator<Thread> threadIterator = threadSet.iterator();
                        for (int i = 0; i < threadSet.size() - minNumberOfThreads; i++) {
                            Thread thread = threadIterator.next();
                            if (thread.getState() == Thread.State.WAITING) {
                                thread.interrupt();
                            }
                        }
                    }
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
        System.out.println(Thread.currentThread().getName() + " started");
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
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
        synchronized (threadSet){
            threadSet.remove(Thread.currentThread());
        }
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}
