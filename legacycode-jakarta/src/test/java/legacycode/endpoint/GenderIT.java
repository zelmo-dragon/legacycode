package legacycode.endpoint;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import legacycode.util.WebContext;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class GenderIT {

    private static final String ENDPOINT = String.join("/", WebContext.API_BASE_URL, "entity", "gender");

    private static final String MALE_JSON = """
            {
                "name": "Male",
                "code": "M",
                "description": "Male humain"
            }
            """;

    private static final String FEMALE_JSON = """
            {
                "name": "Female",
                "code": "F",
                "description": "Female humain"
            }
            """;

    public GenderIT() {
    }

    @Test
    public void testEndpointIsOnline() {

        RestAssured
                .when()
                .get(ENDPOINT)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testDefaultCreate() {

        for (var json : List.of(MALE_JSON, FEMALE_JSON)) {

            RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .body(json)
                    .when()
                    .post(ENDPOINT)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_CREATED);
        }
    }
}
