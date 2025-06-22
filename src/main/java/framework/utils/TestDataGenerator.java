package framework.utils;

import com.github.javafaker.Faker;

public class TestDataGenerator {

    private static final Faker faker = new Faker();

    public static String generatePassword() {
        return faker.internet().password(8, 16);
    }

}
