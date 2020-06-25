package org.example.services;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesService {
    private final static Logger LOGGER = Logger.getLogger(PropertiesService.class.getName());

    private final static String PROPERTIES_NAME = "config.properties";

    public void saveProperty(String key, String value) {
        File configFile = new File(PROPERTIES_NAME);

        try {
            Properties props = new Properties();
            props.setProperty(key, value);
            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "default auth token");
            writer.close();
        } catch (IOException ioe) {
            LOGGER.log(Level.INFO, "Error on writing to the Properties File", ioe);
        }
    }

    public String loadProperty(String key) {
        File configFile = new File("config.properties");
        String value = null;

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            value = props.getProperty(key);
            reader.close();
        } catch (IOException ioe) {
            LOGGER.log(Level.INFO, "Error on reading from the Properties File", ioe);
        }

        return value;
    }
}
