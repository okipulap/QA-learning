package apiTests.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class ApiBaseClient {

    protected final RequestSpecification spec;
    protected final String endpoint = "/pet";

    public ApiBaseClient(RequestSpecification spec) {
        this.spec = spec;
    }

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

    protected Response getById(String endpoint, int id) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("id", id)
                .when()
                .get(endpoint + "{id}")
                .then()
                .extract()
                .response();
    }

    protected Response delete(String endpoint, int id) {
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
