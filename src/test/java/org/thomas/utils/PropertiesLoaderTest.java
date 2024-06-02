package org.thomas.utils;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesLoaderTest {

    @Test
    void testLoadProperties() {
        //execute
        Properties properties = PropertiesLoader.loadProperties();
        //verify
        assertNotNull(properties);
    }

    @Test
    void testGetIntValue() {
        //setup
        Properties properties = new Properties();
        properties.setProperty("test", "1");
        //execute
        Object result = PropertiesLoader.getIntValue(properties, "test");
        //verify
        assertInstanceOf(Integer.class, result);
        assertEquals(1, (int) result);
    }

    @Test
    void testGetIntValue_exception() {
        //setup
        Properties properties = new Properties();
        properties.setProperty("test", "test");
        //execute
        assertThrows(NumberFormatException.class, () -> {
            PropertiesLoader.getIntValue(properties, "test");
        });
    }
}