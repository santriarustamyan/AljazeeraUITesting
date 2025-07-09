package framework.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for loading and accessing application configuration properties.
 */
public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final Properties properties = new Properties();
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    static {
        try (FileInputStream file = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(file);
            logger.info("Configuration file loaded successfully.");
        } catch (IOException e) {
            logger.error("Failed to load configuration file", e);
            throw new RuntimeException("Configuration file not found or unreadable", e);
        }
    }

    /**
     * Retrieves a configuration value as a String.
     *
     * @param key the property key
     * @return the property value
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves a configuration value as a boolean.
     *
     * @param key the property key
     * @return the property value parsed as boolean
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    /**
     * Retrieves the MailSlurp API key from environment variables.
     *
     * @return the MailSlurp API key
     */
    public static String getMailSlurpApiKey() {
        String key = System.getenv("MAILSLURP_API_KEY");
        if (key == null || key.isEmpty()) {
            key = dotenv.get("MAILSLURP_API_KEY");
        }
        if (key == null || key.isEmpty()) {
            throw new RuntimeException("MAILSLURP_API_KEY environment variable is not set");
        }
        return key;
    }
}
