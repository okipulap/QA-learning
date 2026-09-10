package apiTests.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public abstract class ApiBaseClient {

    protected final RequestSpecification spec;

    protected ApiBaseClient(RequestSpecification spec) {
        this.spec = spec;
    }

    protected Response post(String endpoint, Object body) {
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

    protected Response get(String endpoint) {
        return RestAssured.given()
                .spec(spec)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response getWithPathParam(String endpoint, Long id) {
        return RestAssured.given()
                .spec(spec)
                .pathParam("id", id)
                .when()
                .get(endpoint + "/{id}")
                .then()
                .extract()
                .response();
    }

    protected Response getWithQueryParams(String endpoint, Map<String, String> queryParams) {
        RequestSpecification request = RestAssured.given().spec(spec);
        queryParams.forEach(request::queryParam);
        return request.when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

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