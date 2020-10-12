package org.jschool.multithreading;

import java.util.*;

/**
 * Класс реализует интерфейс ThreadPool (пул потоков), с изменяющимся количеством потоков,
 * при создании указываются минимальное и максимальное количество потоков (по умолчанию 8 и 16 соответственно)
 *
 * У данной реализации есть два допущения:
 * (Определение "занятости" потока-провайдера определяется по его состоянию ( != WAITING),
 * в виду этого задания на исполнение не должны переводить потоки-провайдеры в состояние WAITING,
 * иначе возможна некорректная работа (прерывания и заврешения потоков-провайдеров исполняющих полезную работу,
 * или несоздание дополнительных потоков-провайдеров, когда это будет необходимо.)
 *
 * (Создание дополнительных потоков-провайдеров реализовано в методе execute(),  это может привести к тому, что
 * при размещении в очень короткий промежуток времени большого количества заданий (частое исполнение execute())
 * дополнительные потоки-провайдеры могут не создаться ввиду того что поток-демон "не успеет разбудить"
 * существующие потоки-провайдеры.
 * Можно уменьшить время "сна" потока-демона или добавить в метод execute() sleep или wait на короткий промежуток времени,
 * либо реализовать создание дополнительных потоков-провайдеров в поток-демон)
 */
public class ScalableThreadPool implements ThreadPool {
    private final int minNumberOfThreads;
    private final int maxNumberOfThreads;
    private final LinkedList<Runnable> taskList = new LinkedList<>();
    private final Set<Thread> threadSet = new HashSet<>();


    public ScalableThreadPool() {
        this(8, 16);
    }

    public ScalableThreadPool(int minNumberOfThreads, int maxNumberOfThreads) {
        this.minNumberOfThreads = minNumberOfThreads;
        this.maxNumberOfThreads = maxNumberOfThreads;
    }

    /**
     * Метод обеспечивает запуск пула потоков.
     * Инициализирует и запускает поток-демон, необходимый для "пробуждения" и прерывания потоков-провайдеров.
     * Инициализирует и запускает потоки-провайдеры в минимальном количестве, указанном при создании.
     * Учет потоков-провайдеров реализован в хэшсете threadSet.
     */
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

    /**
     * Метод обеспечивает размещение указанного задания - объекта (Runnable task), в очередь (taskList) на исполнение.
     * Также если потоков-провайдров меньше чем максимальное количество, указанное при создании, а существующие
     * все заняты (не WAITING), создает еще один поток провайдер (также добавляет его в threadSet)
     * @param task Runnable  объект - задание для исполнения
     */
    @Override
    public void execute(Runnable task) {

        synchronized (taskList) {
            taskList.add(task);
        }

        synchronized (threadSet) {
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
                    threadSet.add(thread);
                }
            }
        }
    }

    /**
     * Алгоритм работы потока-демона. В бесконечном цикле проверяется наличие заданий в очереди (taskList),
     * при отстутствии таковых проверяет количество потоков-провайдеров (в хэшсете threadSet),
     *      если их больше минимального количества указанного при создании проверяет состояние потоков-провайдеров
     *      (в количестве превышающем минимальное) если состояние WAITING, то поток-провайдер прерывается,
     *      затем поток-демон "засыпает" на секунду;
     * при наличии задания в очереди "пробуждает" один из "спящих" потоков-провайдеров.
     * (wait и notify вызываются на экземпляре ScalableThreadPool).
     * (Данный алгоритм может не обеспечить на одном цикле потока-демона прерывание потоков-провайдеров
     * в необходимом количестве, но при реализации через While и инкременте счетчика после проверки состояния
     * потока-провайдера (=WAITING) возможна ситуация когда количество "спящих" потоков-провайдеров будет меньше,
     * чем необходимо к возвращению к минимальному числу потоков, и тогда будет метод next итератора "выбросит"
     * NoSuchElementException. Я посчитал, что реализовывать в синхронизированном участке более сложную логику
     * проверки нецелесообразно, ввиду того, что в случае отсутствия новых заданий операция повториться
     * на следующем цикле)
     */
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
    /**
     * Алгоритм работы потока-провайдера. Бесконечный цикл (пока не прерван), после запуска "засыпает" (wait()).
     * После "пробуждения" (реализовано в потоке-демоне) берет первую в очереди (taskList) задачу на исполнение
     * и исполняет её. (wait и notify вызываются на экземпляре FixedThreadPool)
     * В случае прерывания удаляет ссылку на себя из хэшсета потоков-провайдеров threadSet'а и завершается.
     */
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
