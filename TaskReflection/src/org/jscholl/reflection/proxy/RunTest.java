package org.jscholl.reflection.proxy;

import java.util.Scanner;

public class RunTest {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
//        Calculator proxyCalc = CachingInvocationHandler.proxyFactory(calculator);
        Calculator metricCalc = PerformanceProxy.proxyFactory(calculator);
        while (true) {
            try {
                Thread.sleep(50);
                System.out.print("Для вычисления факториала числа введите целое число: ");
                Scanner input = new Scanner(System.in);
                System.out.println(metricCalc.calc(input.nextInt()));
            } catch (Throwable e) {
                System.out.println("Что-то пошло не так :(");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
