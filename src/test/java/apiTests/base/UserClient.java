package apiTests.base;

import apiTests.models.user.User;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserClient extends ApiBaseClient {

    private static final String USER_ENDPOINT = "/user";

    public UserClient() {
        super(RequestSpec.defaultLocalSpec());
    }

    @Step("Создание юзера")
    public User postUser(User request) {
        return post(USER_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/user-response-schema.json"))
                .extract()
                .as(User.class);
    }

    @Step("Создание списка юзеров")
    public List<User> postUsersWithList(List<User> request) {
        return post(USER_ENDPOINT + "/createWithList", request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(new TypeRef<>() { });
    }

    @Step("Получение юзера по его username: {username}")
    public User getUserByUsername( String username) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/user-response-schema.json"))
                .extract()
                .as(User.class);
    }

    @Step("Получение юзера по несуществующему username")
    public Response getUserExpected404(String username) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }

    @Step("Изменение юзера по его username: {username}")
    public User putUserByUsername(User request, String username) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("username", username)
                .body(request)
                .when()
                .put(USER_ENDPOINT + "/{username}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/user-response-schema.json"))
                .extract()
                .as(User.class);
    }

    @Step("Изменение юзера по несуществующему username")
    public Response putUserByUsernameExpected404(User request, String username) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("username", username)
                .body(request)
                .when()
                .put(USER_ENDPOINT + "/{username}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }

    @Step("Удаление юзера по username: {username}")
    public Response deleteUser(String username) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("username", username)
                .when()
                .delete(USER_ENDPOINT + "/{username}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }

    @Step("Удаление заказа с несуществующим username")
    public Response deleteUserExpected404(String username) {
        return RestAssured.given()
                .spec(RequestSpec.publicSpec())
                .pathParam("username", username)
                .when()
                .delete(USER_ENDPOINT + "/{username}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }
}

