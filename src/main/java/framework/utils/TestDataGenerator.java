package framework.utils;

import com.github.javafaker.Faker;

/**
 * Utility class for generating random test data.
 */
public class TestDataGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a random password with length between 8 and 16 characters.
     *
     * @return random password string
     */
    public static String generatePassword() {
        return faker.internet().password(8, 16);
    }

    /**
     * Generates a random first name.
     *
     * @return random first name string
     */
    public static String generateFirstName() {
        return faker.name().firstName();
    }

    /**
     * Generates a random last name.
     *
     * @return random last name string
     */
    public static String generateLastName() {
        return faker.name().lastName();
    }
}
