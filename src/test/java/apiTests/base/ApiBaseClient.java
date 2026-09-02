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

    //создать новую спеку, вместо content-type сделать x-www-form-urlencoded и сделать отправление url-encoded параметров
//    @Step("Изменение объекта с помощью формы")
//    protected Response updateWithFormData(String endpoint, Object body, int id) {
//        return RestAssured.given()
//                .spec(spec)
//                .pathParam("id", id)
//                .body(body)
//                .when()
//                .post(endpoint + "/{id}")
//                .then()
//                .extract()
//                .response();
//    }

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

    @Step("Получение объекта по статусу")
    protected Response getByStatus(String endpoint, String status) {
        return RestAssured.given()
                .spec(spec)
                .queryParam("status", status)
                .when()
                .get(endpoint + "/findByStatus")
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
