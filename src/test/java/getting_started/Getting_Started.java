package getting_started;

import io.restassured.response.Response;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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

    @Test
    public void validate_json_response(){
        given()
                .baseUri("https://restcountries.eu/rest/v2").
        when()
                .get("alpha/USA").
        then()
                .statusCode(200)
                .body(
                        "name",equalTo("United States of America"),
                        "alpha3Code",equalTo("USA"),
                        "altSpellings",hasItem("US"),
                        "currencies[0].symbol", equalTo("$"),
                        "languages[0].name",equalTo("English")
                );
    }


    @Test @Ignore
    public void validate_xml_response(){
        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("q","London,uk")
                .queryParam("appid","API_KEY")
                .queryParam("mode","xml").
        when()
                .get("/weather").
        then()
                .statusCode(200)
                .body(
                        "current.city.@name",equalTo("London"),
                        "current.city.country",equalTo("GB"),
                        "current.temperature.@unit",equalTo("kelvin")
                );
    }

    @Test
    public void extract_response_date(){
        Response res =
        given()
                .baseUri("https://restcountries.eu/rest/v2").
        when()
                .get("alpha/USA").
        then()
                .extract().response();
        System.out.println(res.asString());
    }

    @Test @Ignore
    public void extract_single_value(){
       String temperature =
        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("q","London,uk")
                .queryParam("appid","API_KEY")
                .queryParam("mode","xml").
        when()
                .get("/weather").
        then()
                .statusCode(200)
                .body(
                        "current.city.@name",equalTo("London"),
                        "current.city.country",equalTo("GB"),
                        "current.temperature.@unit",equalTo("kelvin")
                )
                .extract().path("current.temperature.@value");

       System.out.println(temperature);
    }

    @Test
    public void verify_status_line(){
        given()
                .baseUri("https://api.printful.com").
        when()
                .get("/variant/1").
        then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found");
    }
}
