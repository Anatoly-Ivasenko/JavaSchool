package org.jschool.stream;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MyStream<T> {

    private final List<T> list;

    private MyStream(List<? extends T> list) {
        this.list = new ArrayList<>(list);
    }

    public static<T> MyStream<T> of(T... elements) {
        return new MyStream<>(Arrays.asList(elements));
    }

    public MyStream<T> filter (Predicate<? super T> predicate) {
        List<T> newList = new ArrayList<>();
        for (T element : list) {
            if (predicate.test(element)) {
                newList.add(element);
            }
        }
        return new MyStream<>(newList);
    }

    public<R> MyStream<R> map(Function<? super T, ? extends R> mapper) {
        List<R> newList = new ArrayList<>();
        for (T element : list) {
            newList.add(mapper.apply(element));
        }
        return new MyStream<>(newList);
    }

    public MyStream<T> distinct() {
        List<T> newList = new ArrayList<>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return new MyStream<>(newList);
    }

    public void forEach(Consumer<? super T> method) {
        for (T t : list) {
            method.accept(t);
        }
    }
}
