package misc_operations;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.config.XmlConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

public class Misc {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void specify_port(){
        when()
                .get("rest/v2/alpha/co").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void connect_whatssup(){
        when()
                .get("/whatssup").
        then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void validate_response_time(){
        when()
                .get("/whatssup").
        then()
                .time(lessThan(900L), TimeUnit.MILLISECONDS);
    }

    @Test
    public void validate_xml_namespace(){

        XmlConfig xmlc = XmlConfig.xmlConfig().declareNamespace("perctg","\"https://wwww.google.com\"");

        given()
                .config(RestAssured.config().xmlConfig(xmlc)).
        when()
                .get("/student/963/score").
        then()
                .log().all()
                .body("student.score[0]",equalTo("369"))
                .body("student.grouping[1]",equalTo("99.66"));
    }

    @Test
    public void validate_response_using_response_part(){

        Response res =
            when()
                    .get("/get-article/bash").
            then()
                .log().all()
                .extract().response();

        String href = res.path("href");
        String articleId = res.path("articleId");
        String articleUrl = res.path("articleUrl");

        Assert.assertTrue(articleUrl.equals(href+"/"+articleId));
    }

    @Test
    public void validate_response_aware_matcher_example(){

        when()
                .get("/get-article/bash").
        then()
                .log().all()
                .body("articleUrl",response -> equalTo(response.path("href").toString() + "/" +
                        response.path("articleId").toString()));
    }

}
