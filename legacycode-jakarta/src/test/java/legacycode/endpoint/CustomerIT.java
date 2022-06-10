package legacycode.endpoint;

import io.restassured.RestAssured;
import legacycode.util.WebContext;
import org.junit.jupiter.api.Test;

public class CustomerIT {

    private static final String ENDPOINT = String.join("/", WebContext.API_BASE_URL, "entity", "customer");

    public CustomerIT() {
    }

    @Test
    public void testEndpointIsOnline() {

        RestAssured
                .when()
                .get(ENDPOINT)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
