package org.jschool.generics;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<T> implements CountMap<T>  {
    private Map<T, Integer> countMap;

    public CountMapImpl() {
        this.countMap = new HashMap<>();
    }

    public void add(T element) {
        countMap.compute(element, (elem, counter) -> (counter == null) ? 1 : counter + 1);
    }

    public int getCount(T element) {
        return countMap.get(element);
    }

    public int remove(T element) {
        Integer counter = countMap.getOrDefault(element, 0);
        countMap.put(element, counter - 1);
        return counter;
    }

    public int size() {
        return countMap.size();
    }

    public void addAll(CountMap<? extends T> source) {
        Map<? extends T,Integer> addMap = (Map<? extends T, Integer>) source.toMap();
        addMap.forEach((addElement, addCounter) -> countMap.merge(addElement, addCounter, Integer::sum));
    }

    public Map<T, Integer> toMap() {
        return countMap;
    }

    public void toMap(Map<? super T, Integer> destination) {
        destination.clear();
        countMap.forEach(destination::put);
    }
}
