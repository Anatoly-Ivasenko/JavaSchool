package org.jschool.iterator;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MyIteratorTest {
    private Object[] testArray = {"First", "Second", "Third"};


    @Test
    public void hasNextTest() {
        MyIterator iteratorTest = new MyIterator(testArray);
        assertTrue("hasNext() must be True", iteratorTest.hasNext());
        iteratorTest.next();
        iteratorTest.next();
        iteratorTest.next();
        assertFalse("hasNext() must be False", iteratorTest.hasNext());
    }

    @Test
    public void nextTest() {
        MyIterator iteratorTest = new MyIterator(testArray);
        Object[] actual = {"First", "Second", "Third"};
        for (int i = 0; i < 3; i++) {
            assertEquals("next() is fail",iteratorTest.next(), actual[i]);
        }
    }

    @Test
    public void nextTestIfOver() {
        MyIterator iteratorTest = new MyIterator(testArray);
        for (int i = 0; i < 3; i++) {
            iteratorTest.next();
        }
        Throwable thrown = assertThrows(NoSuchElementException.class, () -> iteratorTest.next());
        assertEquals("Not valid Exception message", thrown.getMessage(), "Exceeded size of array");
    }
}