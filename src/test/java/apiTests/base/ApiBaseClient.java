package apiTests.base;

import apiTests.specs.RequestSpec;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class ApiBaseClient {

    protected final RequestSpecification spec;

    public ApiBaseClient(RequestSpecification spec) {
        this.spec = spec;
    }

    protected Response create(String endpoint, Object body) {
        return RestAssured.given()
                .spec(spec)
                .body(body)
                .post("pet")
                .then()
                .extract()
                .response();
    }
}
