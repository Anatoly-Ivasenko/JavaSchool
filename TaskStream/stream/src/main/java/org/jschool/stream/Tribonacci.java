package org.jschool.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tribonacci {

    public static List<Long> tribonacci(int n) {
        return Stream.iterate(new long[]{1L, 1L, 1L}, i -> new long[]{i[1], i[2], i[0] + i[1] + i[2]})
                .limit(n)
                .map(i -> i[0])
                .collect(Collectors.toList());
    }
}
