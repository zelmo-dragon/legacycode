package legacycode.endpoint;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import legacycode.util.WebContext;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GenderIT {

    private static final String ENDPOINT = String.join("/", WebContext.API_BASE_URL, "entity", "gender");

    public GenderIT() {
    }

    @BeforeAll
    public static void setup() {

    }

    @Test
    public void testEndpointIsOnline() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testFilter() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .queryParam("code[eq]", "M")
                .when()
                .get(ENDPOINT)
                .then()
                .body("size", Matchers.is(1))
                .body("data[0].id", Matchers.is(DataSet.MALE_ID))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testFind() {
        var path = String.join("/", ENDPOINT, DataSet.FEMALE_ID);
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .body("id", Matchers.is(DataSet.FEMALE_ID))
                .body("name", Matchers.is("Female"))
                .body("code", Matchers.is("F"))
                .body("description", Matchers.is("A female humain"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testCreate() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(DataSet.SAMPLE_JSON_DATA)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testUpdate() {
        var updatedData = DataSet.MALE_JSON_DATA
                .replace("A male humain", "A male humain updated");

        var path = String.join("/", ENDPOINT, DataSet.MALE_ID);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(updatedData)
                .when()
                .put(path)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testDelete() {
        var path = String.join("/", ENDPOINT, DataSet.FEMALE_ID);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .delete(path)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    private static final class DataSet {

        private static final String MALE_ID = "09ee5d9d-bf9b-4b5d-aad0-19117eb8da34";

        private static final String FEMALE_ID = "337ac663-48da-4a97-ad55-062a2c2ebb6d";

        private static final String MALE_JSON_DATA = """
                {
                    "name": "Male",
                    "code": "M",
                    "description": "A male humain"
                }
                """;

        private static final String FEMALE_JSON_DATA = """
                {
                    "name": "Female",
                    "code": "F",
                    "description": "A female humain"
                }
                """;

        private static final String SAMPLE_JSON_DATA = """
                {
                    "name": "Hermaphrodite",
                    "code": "H",
                    "description": "Both sex organ"
                }
                """;

    }
}
