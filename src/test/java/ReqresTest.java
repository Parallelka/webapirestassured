import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTest {
    public static final String BASE_URL = "https://reqres.in";
    public static final String LIST_USERS_URL = "/api/users?page=2";
    public static final String SINGLE_USER_URL = "/api/users/2";
    public static final String CREATE = "/api/users";
    @Test
    public void getAllUsers() {
        get(BASE_URL.concat(LIST_USERS_URL))
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("page", equalTo(2));
    }
    @Test
    public void getSingleUser() {
        get(BASE_URL.concat(SINGLE_USER_URL))
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("data.id", equalTo(2));
    }
    @Test
    public void postCreateUser() {
        String jsonString = "{\"name\" : \"choo\",\"job\" : \"hero\"}";
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(BASE_URL.concat(CREATE));
        request.body(jsonString);
        request.post()
                .then()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("name", equalTo("choo"));
    }
    @Test
    public void putUpdateUser() {
        String jsonString = "{\"name\" : \"choo\",\"job\" : \"superhero\"}";
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(BASE_URL.concat(SINGLE_USER_URL));
        request.body(jsonString);
        request.put()
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("job", equalTo("superhero"));
    }
    @Test
    public void deleteDelUser() {
        delete(BASE_URL.concat(SINGLE_USER_URL))
                .then()
                .statusCode(SC_NO_CONTENT);
    }

}
