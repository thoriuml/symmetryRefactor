package org.thomas.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final Logger logger = LogManager.getLogger(PropertiesLoader.class);
    private static final String PROPERTIES_FILENAME = "application.properties";

    private PropertiesLoader(){ // No public constructor for utility class
        throw new IllegalArgumentException("Utility class");
    }

    public static Properties loadProperties() throws IOException {
        Properties configuration = new Properties();
        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILENAME)) {
            configuration.load(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("Could not load properties", e);
        }
        return configuration;
    }

    //helper method to load int property
    public static int getIntValue(Properties properties, String propertyName) {
        String value = properties.getProperty(propertyName);
        if (!value.isBlank()) {
            return Integer.parseInt(value);
        } else {
            throw new IllegalArgumentException("Property " + propertyName + " is not defined");
        }
    }
}
