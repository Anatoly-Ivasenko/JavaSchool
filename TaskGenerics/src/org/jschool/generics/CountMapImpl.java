package org.jschool.generics;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<T> implements CountMap<T> {
    private Map<T, Integer> countMap;

    public CountMapImpl() {
        this.countMap = new HashMap<>();
    }

    public void add(T element) {
        Integer counter = countMap.getOrDefault(element,0);
        counter++;
        countMap.put(element,counter);
//        countMap.computeIfPresent(element, )  //TODO Попытатся использовать методы computeIfPresent, computeIfAbsent
    }

    public int getCount(T element) {
        return countMap.get(element);
    }

    //current count
    public int remove(T element) {
        Integer counter = countMap.getOrDefault(element, 0);
        countMap.put(element, counter - 1);
        return counter;
    }

    public int size() {
        return countMap.size();
    }

    public void addAll(CountMap<T> source) { //TODO Не работает "Сложение контейнеров"
        Map<T, Integer> addMap = source.toMap();
        addMap.forEach((addElement, addCounter) ->
                countMap.merge(addElement,
                        addCounter,
                        (element, counter) -> counter + addCounter));
    }

    public Map<T, Integer> toMap() {
        return countMap;
    }

    public void toMap(Map destination) {
        destination = countMap;
    }


}
