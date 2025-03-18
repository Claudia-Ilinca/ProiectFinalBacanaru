package ApiTesting;

import java.util.Random;

public class TestData {

    private static final String USERNAME_PREFIX = "ClaudiaIlinca";
    private static final String PASSWORD = "Claudia94@";
    private static final String BOOK_ISBN = "9781449331818";

    public static String generateRandomUsername() {
        String randomNumber = String.format("%05d", new Random().nextInt(100000));
        return USERNAME_PREFIX + randomNumber;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getBookIsbn() {
        return BOOK_ISBN;
    }
}