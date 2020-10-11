package org.jschool.multithreading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ThreadPoolImplDrafts implements ThreadPool{
    private final int numberOfThreads;
    private final Object taskListLock = new Object();
    private final LinkedList<Runnable> taskList = new LinkedList<>();
    private final List<Thread> threadList = Collections.synchronizedList(new ArrayList<>());


    public ThreadPoolImplDrafts() {
        this(10);
    }

    public ThreadPoolImplDrafts(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void start() {

        Thread daemonThread = new Thread(() -> {
            while (true) {
                synchronized (this) {
                    if (taskList.size() == 0) {
                        try {
                            wait(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        notify();
                    }
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                Thread currentThread = Thread.currentThread();
                String id = currentThread.getName();
                System.out.println(id + " started");
                while (!currentThread.isInterrupted()) {
                    synchronized (this) {
                        try {
                            System.out.println(id + " going to wait");
                            wait();
                            System.out.println(id + " waked up");
                            if (taskList.peek() != null) {
                                Runnable tempTask = taskList.poll();
                                Thread utilTask = new Thread(tempTask);
                                utilTask.start();
                                System.out.println(id + " task complete");
                            }
                        } catch (InterruptedException e) {
                            System.out.println(id + " interrupted");
                        }
                    }
                }
                System.out.println(id + " finished");
            });
            thread.start();
            threadList.add(thread);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getThreadGroup().getName() + ": " + Thread.currentThread().getThreadGroup().activeCount());

        threadList.forEach((thread) ->
                System.out.println(thread.getThreadGroup().getName() + "/"
                        + thread.getName() + ": " + thread.getState()));
    }

    @Override
    public void execute(Runnable task) {
        synchronized (this) {
            taskList.add(task);
            System.out.print("|");
        }
    }
}

//    private class PoolsThread extends Thread {
//        private final Object locker;
//        private List<Runnable> taskList;

//        PoolsThread(ThreadGroup group, String name, List<Runnable> taskList, Object locker) {
//            super(group, name);
//            this.taskList = taskList;
//            this.locker = locker;
//        }
//
//        @Override
//        public void run(){
//            String id = getThreadGroup().getName() + "/" + getName();
//            System.out.println(id + " started");
//            while (!isInterrupted()){
//                synchronized (locker) {
//                    try {
//                        System.out.println(id + " going to wait");
//                        locker.wait();
//                    } catch (InterruptedException e) {
//                        System.out.println(id + " interrupted");
//                        Runnable tempTask = taskList.remove(0);
//                        new Thread(tempTask);
//                    }
//                }
//            }
//            System.out.println(id + " finished");
//        }
//    }
//
//
//}
