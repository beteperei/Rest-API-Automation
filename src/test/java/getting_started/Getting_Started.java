package getting_started;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class Getting_Started {

    @Test
    public void simple_get_request(){
        given()
                .baseUri("https://restcountries.eu/rest/v2")
        .when()
                .get("/all")
        .then()
                .statusCode(200);
    }

}
