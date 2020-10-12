package org.jschool.multithreading;

import java.util.*;

/**
 * Класс реализует интерфейс ThreadPool (пул потоков), с фиксированным количеством потоков,
 * указанном при создании (по умолчанию 10)
 */

public class FixedThreadPool implements ThreadPool {
    private final int numberOfThreads;
    private final LinkedList<Runnable> taskList = new LinkedList<>();


    public FixedThreadPool() {
        this(10);
    }

    public FixedThreadPool(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    /**
     * Метод обеспечивает запуск пула потоков.
     * Инициализирует и запускает поток-демон, необходимый для "пробуждения" потоков-провайдеров.
     * Инициализирует и запускает потоки-провайдеры в количестве, указанном при создании.
     */
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


    /**
     * Метод обеспечивает размещение указанного задания - объекта (Runnable task), в очередь (taskList) на исполнение
     * @param task Runnable  объект - задание для исполнения
     */
    @Override
    public void execute(Runnable task) {
        synchronized (taskList) {
            taskList.add(task);
        }
    }

    /**
     * Алгоритм работы потока-демона. В бесконечном цикле проверяется наличие заданий в очереди (taskList),
     * при отстутствии таковых поток "засыпает" на секунду, при наличии "пробуждает" один из "спящих" потоков-провайдеров.
     * (wait и notify вызываются на экземпляре FixedThreadPool).
     */
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

    /**
     * Алгоритм работы потока-провайдера. Бесконечный цикл (пока не прерван), после запуска "засыпает" (wait()).
     * После "пробуждения" (реализовано в потоке-демоне) берет первую в очереди (taskList) задачу на исполнение
     * и исполняет её. (wait и notify вызываются на экземпляре FixedThreadPool)
     */
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

