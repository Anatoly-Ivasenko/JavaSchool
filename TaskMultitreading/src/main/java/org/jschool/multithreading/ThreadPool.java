package org.jschool.multithreading;

public interface ThreadPool {
    void start(); // запускает потоки, которые ждут задач


    void execute(Runnable runnable); //Добавляет задание в очередь на выполнение
}