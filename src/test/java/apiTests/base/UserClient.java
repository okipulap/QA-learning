package apiTests.base;

import apiTests.models.user.User;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

public class UserClient extends ApiBaseClient{

    private static final String USER_ENDPOINT = "/user";

    public UserClient() {
        super(RequestSpec.defaultLocalSpec());
    }

    @Step("Создание юзера")
    public User postUser(User request) {
        return post(USER_ENDPOINT, request)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(User.class);
    }


    @Step("Удаление юзера по id: {id}")
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
}

