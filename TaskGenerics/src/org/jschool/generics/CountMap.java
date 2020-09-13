package org.jschool.generics;

import java.util.Map;

 public interface CountMap<T> {
    void add(T element);

    int getCount(T element);

    int remove(T element);

    int size();

    void addAll(CountMap<? extends T> source);

    Map<? super T, Integer> toMap();

    void toMap(Map<? super T, Integer> destination);
}
