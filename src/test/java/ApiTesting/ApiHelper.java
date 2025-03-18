package ApiTesting;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiHelper {

    private static final String BASE_URL = "https://demoqa.com";

    public Response post(String endpoint, String body) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
    }

    public Response delete(String endpoint, String body, String token) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .delete(endpoint)
                .then()
                .extract().response();
    }

    public Response postWithToken(String endpoint, String body, String token) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
    }
}