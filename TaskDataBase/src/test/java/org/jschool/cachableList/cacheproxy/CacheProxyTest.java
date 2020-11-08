package org.jschool.cachableList.cacheproxy;

import org.jschool.cachableList.Calculator;
import org.jschool.cachableList.CalculatorImpl;
import org.junit.Test;

public class CacheProxyTest {

    @Test
    public void runTestCacheProxy() {
        Calculator calculator = new CacheProxy().cache(new CalculatorImpl());
        System.out.println("3:");
        calculator.fibonachi(3).forEach(System.out::println);
        System.out.println();
        System.out.println("11:");
        calculator.fibonachi(11).forEach(System.out::println);
        System.out.println();
        System.out.println("7:");
        calculator.fibonachi(7).forEach(System.out::println);
    }
}