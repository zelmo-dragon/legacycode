package legacycode.endpoint;

import io.restassured.RestAssured;
import legacycode.util.WebContext;
import org.junit.jupiter.api.Test;

public class DebugIT {

    public DebugIT() {
    }

    @Test
    public void testServerIsOnline() {

        RestAssured
                .when()
                .get(WebContext.BASE_URL)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testApplicationIsOnline() {
        RestAssured
                .when()
                .get(WebContext.APP_BASE_URL)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
