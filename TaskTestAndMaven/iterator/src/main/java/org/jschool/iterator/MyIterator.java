package org.jschool.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MyIterator implements Iterator<Object> {
    private List<Object> listFromArray;
    private int counter;
    private int size;


    public MyIterator(Object[] array) {
        listFromArray = Arrays.asList(array);
        size = listFromArray.size();
        counter = -1;
    }

    public boolean hasNext() {
        return counter < (size - 1);
    }

    public Object next() {
        counter ++;
        if (counter > (size - 1)) {
            throw new NoSuchElementException("Exceeded size of array");
        }
        return listFromArray.get(counter);
    }
}
