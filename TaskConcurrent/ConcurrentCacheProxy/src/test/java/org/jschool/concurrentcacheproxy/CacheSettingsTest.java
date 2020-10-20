package org.jschool.concurrentcacheproxy;

import org.jschool.calc.Calculator;
import org.jschool.calc.CalculatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheSettingsTest {
    private static CacheSettings cacheSettingsDefault;

    @BeforeClass
    public static void setUp() throws Exception {
        Calculator newCalc = new CalculatorImpl();
        cacheSettingsDefault = new CacheSettings(newCalc.getClass().getMethod("calc", int.class));
        System.out.println(cacheSettingsDefault.cached());
    }

    @Test
    public void cached() {
        assertTrue(cacheSettingsDefault.cached());
    }

    @Test
    public void inFile() {
        assertFalse(cacheSettingsDefault.inFile());
    }

    @Test
    public void keyPrefix() {
        String actual = "calc";
        String expected = cacheSettingsDefault.keyPrefix();
        assertEquals(expected, actual);
    }

    @Test
    public void getMethodParameterTypes() {
        Class<?>[] actual = new Class<?>[1];
        actual[0] = int.class;
        Class<?>[] expected = cacheSettingsDefault.getMethodParameterTypes();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void resultIsAssignableToList() {
        assertFalse(cacheSettingsDefault.resultIsAssignableToList());
    }

    @Test
    public void toZip() {
        assertFalse(cacheSettingsDefault.toZip());
    }

    @Test
    public void argsForKey() {
        Class<?>[] actual = {};
        Class<?>[] expected = cacheSettingsDefault.argsForKey();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void listCapacity() {
        int actual = 0;
        int expected = cacheSettingsDefault.listCapacity();
        assertEquals(expected, actual);
    }

    @Test
    public void getMethodName() {
        String actual = "calc";
        String expected = cacheSettingsDefault.getMethodName();
    }


}