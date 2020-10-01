package org.jschool.cacheproxy;

import org.jschool.calc.Calculator;
import org.jschool.calc.CalculatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CacheUtilsTest {
    private static CacheSettings cacheSettingsDefault;

    @BeforeClass
    public static void setUp() throws Exception {
        Calculator newCalc = new CalculatorImpl();
        cacheSettingsDefault = new CacheSettings(newCalc.getClass().getMethod("calc", int.class));
        System.out.println(cacheSettingsDefault.cached());
    }

    @Test
    public void saveAndGet() throws Exception {
        File testFile = Paths.get("src/test/resources/test").toFile();
        int actual = 34;
        CacheUtils.save(actual, cacheSettingsDefault, testFile);
        Object expected = CacheUtils.getCache(testFile);
        assertEquals(expected, actual);
    }

    @Test
    public void saveAndGetZip() throws Exception {
        File testFile = Paths.get("src/test/resources/testzip").toFile();
        int actual = 34;
        CacheUtils.saveToZip(actual, cacheSettingsDefault, testFile);
        Object expected = CacheUtils.getCacheFromZip(testFile);
        assertEquals(expected, actual);
    }
}