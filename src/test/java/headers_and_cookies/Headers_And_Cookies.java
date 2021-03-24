package headers_and_cookies;

import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Headers_And_Cookies {

    @Test
    public void sending_request_headers(){

        given()
                .baseUri("http://data.fixer.io/api/")
                .queryParam("access_key","API_KEY")
                .queryParams("Symbols","USD")
                .header("If-None-Match","0903785e10000036e721b64000000001")
                .header("If-Modified-Since","Wed, 24 Mar 2021 01:36:25 GMT").
        when()
                .get("/latest").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_headers_object(){

        HashMap<String,Object> headers = new HashMap<>();
        headers.put("If-None-Match","0903785e10000036e721b64000000001");
        headers.put("If-Modified-Since","Wed, 24 Mar 2021 01:36:25 GMT");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("Connection","keep-alive");

        given()
                .baseUri("http://data.fixer.io/api/")
                .queryParam("access_key","API_KEY")
                .queryParams("Symbols","USD")
                .headers(headers).
        when()
                .get("/latest").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_request_cookies(){

        given()
                .baseUri("http://data.fixer.io/api/")
                .queryParam("access_key","API_KEY")
                .queryParams("Symbols","USD")
                .cookie("user","ajhds").
        when()
                .get("/latest").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_cookies_using_builder(){

        Cookie cookie = new Cookie.Builder("usertype","int")
                        .setSecured(true)
                        .setComment("test cookie")
                        .build();

        given()
                .baseUri("http://data.fixer.io/api/")
                .queryParam("access_key","API_KEY")
                .queryParams("Symbols","USD")
                .cookie(cookie).
        when()
                .get("/latest").
        then()
                .log().cookies()
                .statusCode(200);
    }

    @Test
    public void validate_response_header(){

        given()
                .baseUri("http://data.fixer.io/api/")
                .queryParam("access_key","API_KEY")
                .queryParams("Symbols","USD").
        when()
                .get("/latest").
        then()
                .log().all()
                .statusCode(200)
                .header("Server","cloudflare");
    }

    @Test
    public void extract_response_header(){

        Headers headers = given()
                .baseUri("http://data.fixer.io/api/")
                .queryParam("access_key","API_KEY")
                .queryParams("Symbols","USD").
        when()
                .get("/latest").
        then()
                .statusCode(200)
                .extract().headers();

        System.out.println(headers.getValue("CF-RAY"));
        System.out.println(headers.getValue("access-control-allow-methods"));
        System.out.println(headers.getValue("Transfer-Encoding"));
    }

    @Test
    public void extract_response_cookies(){

        Map<String,String> cookies =

            given()
                    .baseUri("http://data.fixer.io/api/")
                    .queryParam("access_key","API_KEY")
                    .queryParams("Symbols","USD").
            when()
                    .get("/latest").
            then()
                    .statusCode(200)
                    .extract().cookies();

        System.out.println(cookies.get("__cfduid"));
    }
}
