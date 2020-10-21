package org.jschool.concurrentcacheproxy;

import org.jschool.calc.Calculator;
import org.jschool.calc.CalculatorImpl;

public class RunTest {

        public static void main(String[] args) {
                CacheProxy cacheProxy = new CacheProxy("src/test/resources");
                Calculator cachedCalc = cacheProxy.cache(new CalculatorImpl());

                for (int i = 0; i < 10; i++) {
                        Thread thread = new Thread(() -> {
                                int argument = (int) (1 + Math.round(Math.random() * 11));
                                System.out.println(Thread.currentThread().getName() + " started:" + argument);
                                System.out.println(Thread.currentThread().getName() + " finished:" + cachedCalc.calc(argument));
                        });
                        thread.start();
                }

                try {
                        Thread.sleep(5000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }

                for (int i = 0; i < 10; i++) {

                        Thread thread = new Thread(() -> {
                                int argument = (int) (1 + Math.round(Math.random() * 11));
                                System.out.println(Thread.currentThread().getName() + " started:" + argument);
                                System.out.println(Thread.currentThread().getName() + " finished:" + cachedCalc.calc(argument));
                        });
                        thread.start();
                }
        }
}
