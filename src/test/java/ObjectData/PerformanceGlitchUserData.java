package ObjectData;

/**
 * Data object for performance_glitch_user credentials and checkout information.
 */
public class PerformanceGlitchUserData {
    private final String username = "performance_glitch_user";
    private final String password = "secret_sauce";
    private final String firstName = "John";
    private final String lastName = "Doe";
    private final String postalCode = "12345";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }
}