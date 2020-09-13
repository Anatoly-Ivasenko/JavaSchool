package org.jschool.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {
    public static<T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static<T> List<? super T> newArrayList() {
        return new ArrayList<>();
    }

    public static<T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static List<?> limit(List<?> source, int size) {
        return source.subList(0, size - 1);
    }

    public static<T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    public static<T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
       removeFrom.removeAll(c2);
    }

    public static<T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    public static<T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
        for (T item : c1) if (c2.contains(item)) return true;
        return false;
//        return !Collections.disjoint(c1, c2);
    }

    public static<T extends Comparable<? super T>> List<? super T> range(List<? extends T> list, T min, T max) {
        List<? super T> result = new ArrayList<>();
        Collections.sort(list);
        for (T item : list) if ((item.compareTo(min) >= 0) && (item.compareTo(max) <= 0)) result.add(item);
        return result;
    }

    public static<T extends Comparator<? super T>> List<? super T> rangeComparator(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        List<? super T> result = new ArrayList<>();
        list.sort(comparator);
        for (T item : list) if ((comparator.compare(item, min) >= 0) && (comparator.compare(item, max) <= 0)) result.add(item);
        return result;
    }
}


