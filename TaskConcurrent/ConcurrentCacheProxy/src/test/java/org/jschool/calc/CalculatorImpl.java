package org.jschool.calc;

import org.jschool.concurrentcacheproxy.Cache;

public class CalculatorImpl implements Calculator {

    @Override
    @Cache                              // Тесты CacheSettingsTest без этой аннотации не проходят, однако RunTest работает
    public int calc(int number) {
        if (number < 1) {
            throw new RuntimeException("Число должно быть больше еденицы");
        }
        long factorial;
        long internalNumber = number;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (internalNumber == 1L) {
            factorial = 1;
        } else {
            factorial = internalNumber * calc((int) internalNumber - 1);
        }
        if (factorial > 2147483647) throw new RuntimeException("Слишком большое число, нужно использовать long");
        return (int) factorial;
    }
}
