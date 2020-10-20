package org.jschool.calc;

import org.jschool.concurrentcacheproxy.Cache;
import org.jschool.concurrentcacheproxy.CacheType;

public interface Calculator {
    /**
     * Расчет факториала числа.
     * @param number
     */

    @Cache(cacheType = CacheType.IN_FILE)
    int calc (int number);
}
