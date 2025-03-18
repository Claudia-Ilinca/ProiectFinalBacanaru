package ApiTesting;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BookStoreApiTest {

    private ApiHelper apiHelper;

    @BeforeMethod
    public void setUp() {
        apiHelper = new ApiHelper();
    }

    @Test
    public void testBookStoreFullFlow() {
        // Date de test
        String userName = TestData.generateRandomUsername();
        String password = TestData.getPassword();
        String isbn = TestData.getBookIsbn();

        // Pas 1: Creăm contul
        String createBody = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);
        Response createUserResponse = apiHelper.post("/Account/v1/User", createBody);
        Assert.assertEquals(createUserResponse.getStatusCode(), 201, "User creation failed");
        String userId = createUserResponse.jsonPath().getString("userID");

        // Pas 2: Generăm token-ul
        String tokenBody = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);
        Response tokenResponse = apiHelper.post("/Account/v1/GenerateToken", tokenBody);
        Assert.assertEquals(tokenResponse.getStatusCode(), 200, "Token generation failed");
        String token = tokenResponse.jsonPath().getString("token");

        // Pas 3: Adăugăm o carte în colecție
        String addBookBody = String.format("{\"userId\": \"%s\", \"collectionOfIsbns\": [{\"isbn\": \"%s\"}]}", userId, isbn);
        Response addBookResponse = apiHelper.postWithToken("/BookStore/v1/Books", addBookBody, token);
        Assert.assertEquals(addBookResponse.getStatusCode(), 201, "Failed to add book");
        String addedIsbn = addBookResponse.jsonPath().getString("books[0].isbn");
        Assert.assertEquals(addedIsbn, isbn, "Added ISBN does not match");

        // Pas 4: Ștergem cartea din colecție
        String deleteBookBody = String.format("{\"isbn\": \"%s\", \"userId\": \"%s\"}", isbn, userId);
        Response deleteBookResponse = apiHelper.delete("/BookStore/v1/Book", deleteBookBody, token);
        Assert.assertEquals(deleteBookResponse.getStatusCode(), 204, "Failed to delete book");
    }
}