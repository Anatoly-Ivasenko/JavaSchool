package org.jscholl.reflection.proxy;

import javax.swing.*;

public class CalculatorImpl implements Calculator {

    @Override
    public int calc(int number) {
        long factorial = 0L;
        long internalNumber = (long) number;
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        if (internalNumber == 1L) {
            factorial = 1;
        } else {
            factorial = internalNumber * calc((int) internalNumber - 1);
        }
        if (factorial > 2147483647) throw new RuntimeException("Слишком большое число, нужно использовать long");
        return (int) factorial;
    }
}
