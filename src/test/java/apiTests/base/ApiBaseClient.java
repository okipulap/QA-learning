package apiTests.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.*;

public abstract class ApiBaseClient {

    protected final RequestSpecification spec;

    public ApiBaseClient(RequestSpecification spec) {
        this.spec = spec;
    }

    @Step("Создание объекта")
    protected Response create(String endpoint, Object body) {
        return RestAssured.given()
                .spec(spec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    @Step("Изменение объекта")
    protected Response put(String endpoint, Object body) {
        return RestAssured.given()
                .spec(spec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    @Step("Получение объекта по id")
    protected Response getById(String endpoint, Long id) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("id", id)
                .when()
                .get(endpoint + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Step("Удаление объекта")
    protected Response delete(String endpoint, Long id) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("id", id)
                .when()
                .delete(endpoint + "/{id}")
                .then()
                .extract()
                .response();
    }

}
