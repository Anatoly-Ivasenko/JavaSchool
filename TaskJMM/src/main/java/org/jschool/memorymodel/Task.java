package org.jschool.memorymodel;

import java.util.concurrent.Callable;


public class Task<T> {

    /**
     * Объект Callable, устанавливается в конструкторе
     */
    private final Callable<? extends T> callable;

    /**
     * результат работы callable, присваивается в методе calling()
     */
    private T resultCallable;

    /**
     * Исключение, принятое в методе calling() в результате "неуспешной" работы callable
     */
    private RuntimeException exceptionFromCallable;

    /**
     * Флаг указывающий, что callable.call() уже вызывался
     */
    private volatile boolean isCalled = false;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    /**
     * Метод возвращает результат работы callable (является final, устанавливается в конструкторе)
     * Если callable.call() уже вызывался (проверяется флаг isCalled), то метод возвращает резльтат работы callable
     * или бросает исключение брошенное им (сохраненное в exceptionFromCallable).
     * Если callable.call() еще не вызывался, тогда вызывается метод calling(), а затем повторно get()
     * @return T результат работы callable
     */
    public T get() {
//        System.out.println(Thread.currentThread().getName() + " getting");
        if (isCalled) {
            if (resultCallable != null) {
                return resultCallable;
            } else {
                throw exceptionFromCallable;
            }
        } else {
            calling();
            return get();
        }
    }

    /**
     * Метод блокирует объект - экземпляр класса Task, затем проверяет вызывался ли уже callable.call(),
     * если вызывался заканчивает работу и разблокирует объект
     * если не вызывался - вызывает callable.call() и  сохраняет результат или исключение, затем устанваливает
     * флаг isCalled в true.
     */
    private synchronized void calling() {
//            System.out.println(Thread.currentThread().getName() + " entered");
            if (isCalled) {
                return;
            }
            try {
//                System.out.println(Thread.currentThread().getName() + " calculating");
                resultCallable = callable.call();
            } catch (Exception e) {
                exceptionFromCallable = new ExceptionTaskCalling("Exception from " + callable.toString() + ": " + e.getMessage());
            } finally {
                isCalled = true;
            }
    }
}

