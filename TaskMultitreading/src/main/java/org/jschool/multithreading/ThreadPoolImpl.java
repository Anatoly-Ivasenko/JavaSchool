package org.jschool.multithreading;

import java.util.*;

public class ThreadPoolImpl implements ThreadPool{
    private final int numberOfThreads;
    private final Object taskListLock = new Object();
    private final List<Runnable> taskList = Collections.synchronizedList(new ArrayList<>());
    private final Map<Thread, Thread.State> threadMap = Collections.synchronizedMap(new HashMap<>());


    public ThreadPoolImpl() {
        this(10);
    }

    public ThreadPoolImpl(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void start() {
//        ThreadGroup pool = new ThreadGroup("pool");
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
                            Runnable tempTask = taskList.remove(0);
                            new Thread(tempTask);
                            System.out.println(id + " task complete");
                        } catch (InterruptedException e) {
                            System.out.println(id + " interrupted");

                        }
                    }
                }
                System.out.println(id + " finished");
            });
            thread.start();
            threadMap.put(thread, thread.getState());
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.print(pool.getName() + ": " + pool.activeCount() + "---");
        System.out.println(Thread.currentThread().getThreadGroup().getName() + ": " + Thread.currentThread().getThreadGroup().activeCount());

        threadMap.forEach((thread, state) ->
                System.out.println(thread.getThreadGroup().getName() + "/"
                        + thread.getName() + ": " + thread.getState() + " " + state));
//        synchronized (this) {
//            threadMap.forEach((thread, state) -> notify());
//        }



//        while (true) {
//            try {
//                synchronized (this) {
//                    wait(500);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            synchronized (this) {
//                threadMap.forEach((thread, state) -> {
//                    if (taskList.size() > 0) {
//                        notify();
//                    }
//                });
//            }
//        }
    }







    @Override
    public void execute(Runnable task) {
        synchronized (this) {
            taskList.add(task);
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
