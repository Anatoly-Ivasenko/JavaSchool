package org.jschool.calc;

import org.jschool.cacheproxy.Cache;

public class CalculatorImpl implements Calculator {

    @Override
    @Cache                              // Тесты CacheSettingsTest без этой аннотации не проходят, однако RunTest работает
    public int calc(int number) {
        long factorial = 0L;
        long internalNumber = (long) number;
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
