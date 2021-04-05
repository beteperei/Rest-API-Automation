package auth;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Auth {

    @Test
    public void digest_authentication(){

       given()
               .baseUri("https://postman-echo.com")
               .auth().digest("postman","password").
       when()
               .get("/digest-auth").
       then()
               .log().all()
               .statusCode(200);
   }

   @Test
    public void post_a_tweet(){

        String tweet = "This is a tweet from Rest Assured.";

        given()
                .baseUri("https://api.twitter.com/1.1")
                .auth().oauth("API_KEY",
                "API_SECRET_KEY",
                "ACCESS_TOKEN","ACESS_SECRET_TOKEN")
                .param("status",tweet).
        when()
                .post("statuses/update.json").
        then()
                .log().all()
                .statusCode(200);

   }

   @Test
    public void paypal_test(){

       String accessToken = given()
                .baseUri("https://api.sandbox.paypal.com/v1")
                .contentType("application/x-www-form-urlencoded;UTF-8")
                .header("Accept-Language","en_US")
                .param("grant_type","client_credentials")
                .auth().preemptive().basic("CLIENT_ID",
                "SECRET").
        when()
                .post("oauth2/token").
        then()
                .log().all()
                .statusCode(200)
               .extract().path("access_token");

       given()
               .baseUri("https://api.sandbox.paypal.com/v2")
               .contentType("application/json")
               .auth().oauth2(accessToken).
       when()
               .post("/invoicing/generate-next-invoice-number").
       then()
               .log().all()
               .statusCode(200);
    }

}
