package schema_validation;

import org.testng.annotations.Test;

import java.io.File;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
public class Schema_Validation {

    @Test
    public void json_schema_validation(){

        File file = new File("resources/json_schema_1.json");

        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "API_KEY")
                .queryParam("Symbols","USD").
        when()
                .get("/latest").
        then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchema(file));
    }

    @Test
    public void xml_dtd_schema_validation(){

        File file = new File("resources/xml_dtd_schema.dtd");

        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("APPID","API_KEY")
                .queryParam("q","London, uk")
                .queryParam("mode","xml").
        when()
                .get("/weather").
        then()
                .body(matchesDtd(file))
                .log().all()
                .statusCode(200);
    }

    @Test
    public void xml_xsd_schema_validation(){

        File file = new File("resources/xml_xsd_schema.xsd");

        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("APPID","API_KEY")
                .queryParam("q","London, uk")
                .queryParam("mode","xml").
                when()
                .get("/weather").
                then()
                .body(matchesXsd(file))
                .log().all()
                .statusCode(200);
    }

}
