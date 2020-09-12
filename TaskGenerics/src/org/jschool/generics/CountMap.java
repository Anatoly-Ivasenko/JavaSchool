package org.jschool.generics;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

 public interface CountMap<T> {
    void add(T element);

    int getCount(T element);

    //current count
    int remove(T element);

    int size();

    void addAll(CountMap<T> source);

    Map toMap();

    void toMap(Map destination);
}
