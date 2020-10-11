package org.jschool.multithreading;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


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

        Thread daemonThread = new Thread(new DaemonTask());
        daemonThread.setDaemon(true);
        daemonThread.start();

        for (int i = 0; i < minNumberOfThreads; i++) {
            Thread thread = new Thread(new ProviderTask());
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
        synchronized (threadSet) {
            if (threadSet.size() < maxNumberOfThreads) {
                boolean allRun = true;
                for (Thread thread : threadSet) {
                    if (thread.getState() != Thread.State.RUNNABLE) {
                        allRun = false;
                        break;
                    }
                }
                if (allRun) {
                    Thread thread = new Thread(new ProviderTask());
                    thread.start();
                    synchronized (threadSet) {
                        threadSet.add(thread);
                    }
                }

            }
        }
    }
    class DaemonTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (ScalableThreadPool.this.locker) {
                    if (ScalableThreadPool.this.taskList.size() == 0) {
                        try {
                            wait(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        notify();
                    }
                }
            }
        }
    }

    class ProviderTask implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (ScalableThreadPool.this.locker) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (ScalableThreadPool.this.taskList) {
                    Runnable tempTask = ScalableThreadPool.this.taskList.poll();
                    new Thread(tempTask).start();
                }
            }
        }
    }
}




//    class providerTask() implements Runnable {
//
//        @Override
//        public void run(){
//        Thread thread = new Thread(() -> {
//            Thread currentThread = Thread.currentThread();
//            while (!currentThread.isInterrupted()) {
//                synchronized (this) {
//                    try {
//                        wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                synchronized (ScalableThreadPool.this.taskList) {
//                    Runnable tempTask = ScalableThreadPool.this.taskList.poll();
//                    new Thread(tempTask).start();
//                }
//            }
//        }
//        }
//    }

//
//
//}
