package org.jschool.concurrentcacheproxy;

import org.jschool.calc.Calculator;
import org.jschool.calc.CalculatorImpl;

public class RunTest {

        public static void main(String[] args) {
                CacheProxy cacheProxy = new CacheProxy("src/test/resources");
                Calculator cachedCalc = cacheProxy.cache(new CalculatorImpl());
                System.out.println(cachedCalc.calc(3));
                System.out.println(cachedCalc.calc(4));
                System.out.println(cachedCalc.calc(5));
                System.out.println(cachedCalc.calc(10));
                System.out.println(cachedCalc.calc(3));
                System.out.println(cachedCalc.calc(3));
        }

}
