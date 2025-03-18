package ObjectData;

public class StandardUserData {
    private final String username = "standard_user";
    private final String password = "secret_sauce";
    private final String firstName = "John";
    private final String lastName = "Doe";
    private final String postalCode = "12345";

    public String getUsername() {
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("Username cannot be empty");
        }
        return username;
    }

    public String getPassword() {
        if (password == null || password.isEmpty()) {
            throw new IllegalStateException("Password cannot be empty");
        }
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