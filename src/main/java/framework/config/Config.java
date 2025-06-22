package framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties.load(file);
        } catch (IOException e) {
            logger.error("Failed to load configuration file", e);
            throw new RuntimeException("Configuration file not found", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static String getMailSlurpApiKey() {
        String key = System.getenv("MAILSLURP_API_KEY");
        if (key == null) {
            throw new RuntimeException("MAILSLURP_API_KEY env variable is not set");
        }
        return key;
    }
}
