package request_parameters;

import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.*;

public class Request_Parameters {

    @Test
    public void handling_query_parameters(){
        given()
                .baseUri("https://restcountries.eu/rest/v2/")
                .queryParam("fullText",false).
        when()
                .get("/name/india").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void handling_multiple_parameters(){

        HashMap<String,Object> params = new HashMap<>();
        params.put("access_key","API_KEY");
        params.put("Symbols","USD");

        given()
                .baseUri("http://data.fixer.io/api/")
                .queryParams(params).
        when()
                .get("/latest").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void handling_multi_value_parameters(){
        given()
                .baseUri("https://restcountries.eu/rest/v2/")
                .queryParam("codes","col;no;ee;in").
        when()
                .get("/alpha").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void handling_path_parameter(){
        given()
                .baseUri("https://restcountries.eu/rest/v2/")
                .pathParam("currency","usd").
        when()
                .get("/currency/{currency}").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void handling_form_data(){
        given()
                .baseUri("https://postman-echo.com")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .formParam("First Name","Shravya")
                .formParam("Last Name", "Deshmukh").
        when()
                .post("/post").
        then()
                .log().all()
                .statusCode(200);
    }
}
