package org.jschool.calc;

import org.jschool.cacheproxy.Cache;
import org.jschool.cacheproxy.CacheType;

public interface Calculator {
    /**
     * Расчет факториала числа.
     * @param number
     */

    @Cache(cacheType = CacheType.IN_FILE, zipped = true)
    int calc (int number);
}
