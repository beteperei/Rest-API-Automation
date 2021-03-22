package post_put_delete;

import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class Post_Put_Delete {

    @Test
    public void post_request(){

        File file = new File("resources/create_employee.json");

       int id =
       given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
                .body(file).
        when()
                .post("/create").
        then()
                .statusCode(200)
                .body("data.name",equalTo("Mark"))
                .extract().path("data.id");

        System.out.println(id);
    }

    @Test
    public void post_request_using_json_object(){

        JSONObject body = new JSONObject();
        body.put("name","Tim");
        body.put("salary","45678");
        body.put("age","35");

        int id =
                given()
                        .baseUri("http://dummy.restapiexample.com/api/v1")
                        .contentType(ContentType.JSON)
                        .body(body.toString()).
                when()
                        .post("/create").
                then()
                        .statusCode(200)
                        .body("data.name",equalTo("Tim"))
                        .extract().path("data.id");

        System.out.println(id);
    }

    @Test
    public void put_request_using_json_object(){

        JSONObject body = new JSONObject();
        body.put("name","Sam");
        body.put("salary","1000");
        body.put("age","34");

                given()
                        .baseUri("http://dummy.restapiexample.com/api/v1")
                        .contentType(ContentType.JSON)
                        .body(body.toString()).
                when()
                        .put("/update/24").
                then()
                        .statusCode(200);

    }

    @Test
    public void delete_request(){

        String message = given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON).
        when()
                .delete("/delete/24").
        then()
                .statusCode(200)
                .extract().path("message");

        System.out.println(message);
    }
}
