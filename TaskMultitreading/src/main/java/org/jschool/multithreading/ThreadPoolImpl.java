package org.jschool.multithreading;

import java.util.*;

public class ThreadPoolImpl implements ThreadPool{
    private final int numberOfThreads;
    private List<Runnable> taskList = Collections.synchronizedList(new ArrayList<>());
    private Map<Thread, Thread.State> threadMap = Collections.synchronizedMap(new HashMap<>());


    public ThreadPoolImpl() {
        this(10);
    }

    public ThreadPoolImpl(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void start() {
        ThreadGroup pool = new ThreadGroup("pool");
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new PoolsThread(pool,("pool-" + i));
            thread.start();
            threadMap.put(thread, thread.getState());
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(pool.getName() + ": " + pool.activeCount() + "---");
        System.out.println(Thread.currentThread().getThreadGroup().getName() + ": " + Thread.currentThread().getThreadGroup().activeCount());

        threadMap.forEach((thread, state) ->
                System.out.println(thread.getThreadGroup().getName() + "/"
                        + thread.getName() + ": " + thread.getState() + " " + state));

        System.out.println(pool.getParent().getName());


    }

    @Override
    public void execute(Runnable task) {

    }

    private class PoolsThread extends Thread {

        PoolsThread(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run(){
            String id = getThreadGroup().getName() + "/" + getName();
            System.out.println(id + " started");
            while (!isInterrupted()){
                synchronized (this) {
                    try {

                        System.out.println(id + " going to wait");
                        wait();
                    } catch (InterruptedException e) {
                        System.out.println(id + " interrupted");
                        interrupt();
                    }
                }
            }
            System.out.println(id + " finished");
        }
    }


}
