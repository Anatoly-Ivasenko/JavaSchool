package org.jschool.memorymodel.execmanager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionManagerImpl implements ExecutionManager {
    private final Context context = new ContextImpl();
//    private volatile int taskCount;
    private final AtomicInteger completedTaskCount = new AtomicInteger(0);
    private final AtomicInteger failedTaskCount = new AtomicInteger(0);
    private final AtomicInteger interruptedTaskCount = new AtomicInteger(0);
    private volatile boolean isFinished = false;
    private volatile boolean interrupted = false;
    private final Set<Thread> threads = Collections.synchronizedSet(new HashSet<>());
//    private LinkedBlockingQueue<Runnable> taskQueue;


    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
//        taskCount = tasks.length;

        Thread thread = new Thread(() -> this.serviceTask(callback, tasks));
        thread.start();
        return context;
    }



    public class ContextImpl implements Context {

        @Override
        public int getCompletedTaskCount() {
            return ExecutionManagerImpl.this.completedTaskCount.get();
        }

        @Override
        public int getFailedTaskCount() {
            return ExecutionManagerImpl.this.failedTaskCount.get();
        }

        @Override
        public int getInterruptedTaskCount() {
            return ExecutionManagerImpl.this.interruptedTaskCount.get();
        }

        @Override
        public void interrupt() {
            interrupted = true;
            synchronized (threads) {
                threads.forEach(thread -> {
                    if (thread.isAlive()) thread.interrupt();
                });
            }
        }

        @Override
        public boolean isFinished() {
            return ExecutionManagerImpl.this.isFinished;
        }
    }

    private void serviceTask(Runnable callback, Runnable... tasks) {

        for (Runnable task : tasks) {
            if (!interrupted) {
                Thread thread = new Thread(() -> providerTask(task));
                threads.add(thread);
                thread.start();
            } else {
                interruptedTaskCount.incrementAndGet();
            }
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        if (!interrupted) {
            Thread callbackThread = new Thread(callback);
            callbackThread.start();
            threads.add(callbackThread);

            try {
                callbackThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isFinished = true;
        }
    }

    private void providerTask(Runnable task) {
        System.out.println(Thread.currentThread().getName() + " started");
        if (Thread.currentThread().isInterrupted()) {
            interruptedTaskCount.incrementAndGet();
            return;
        }
        task.run();
        completedTaskCount.incrementAndGet();
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}
