package org.jschool.cachablefibonachi;

import java.util.List;

public interface Calculator {
    @Cachable(H2DB.class)
    List<Integer> fibonachi(int n);
}
