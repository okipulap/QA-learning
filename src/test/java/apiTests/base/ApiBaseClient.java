package apiTests.base;

import apiTests.specs.RequestSpec;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;

import java.io.File;

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

    @Step("Загрузка изображения")
    protected Response uploadImage(String endpoint, Long id, File image) {
        return RestAssured.given()
                .given()
                .spec(RequestSpec.uploadImageSpec())
                .pathParam("petId", id)
                .multiPart("additionalMetadata", "test metadata")
                .multiPart("file", image, "image/jpeg")
                .when()
                .post(endpoint + "/{petId}/uploadImage")
                .then()
                .extract()
                .response();
    }

    @Step("Изменение объекта с помощью формы")
    protected Response updateWithFormData(String endpoint, Object body, Long id, String name, String status) {
        return RestAssured.given()
                .spec(RequestSpec.formDataSpec())
                .pathParam("id", id)
                .formParam("name", name)
                .formParam("status", status)
                .when()
                .post(endpoint + "/{id}")
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
