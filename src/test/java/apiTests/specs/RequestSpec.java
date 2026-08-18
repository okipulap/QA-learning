package apiTests.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {

    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/")
                .setBasePath("v2/")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
}
