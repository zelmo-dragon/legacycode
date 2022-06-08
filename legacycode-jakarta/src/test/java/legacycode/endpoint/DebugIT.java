package legacycode.endpoint;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class DebugIT {

    public DebugIT() {
    }

    @Test
    public void testServerIsOnline() {

        RestAssured
                .when()
                .get("http://localhost:8080")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testApplicationIsOnline() {
        RestAssured
                .when()
                .get("http://localhost:8080/legacycode")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
