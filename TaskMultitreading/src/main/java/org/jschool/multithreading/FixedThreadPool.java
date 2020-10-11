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
                tempTask = FixedThreadPool.this.taskList.poll();
            }
            tempTask.run();
        }
    }

//    class DaemonTask implements Runnable {
//
//        @Override
//        public void run() {
//            while (true) {
//                synchronized (FixedThreadPool.this.taskList) {
//                    System.out.println(FixedThreadPool.this.taskList.size());
//                    if (FixedThreadPool.this.taskList.size() == 0) {
//                        try {
//                            wait(200);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        notify();
//                    }
//                }
//            }
//        }
//    }
//
//    class ProviderTask implements Runnable {
//
//        @Override
//        public void run() {
//            while (!Thread.currentThread().isInterrupted()) {
//                synchronized (this) {
//                    System.out.println(Thread.currentThread().getName());
//                    try {
//                        wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                synchronized (taskList) {
//                    Runnable tempTask = FixedThreadPool.this.taskList.poll();
//                    new Thread(tempTask).start();
//                }
//            }
//        }
//    }
}

