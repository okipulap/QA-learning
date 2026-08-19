package apiTests.specs;

import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {

    private static final String API_KEY = Dotenv.load().get("PETSTORE_API_KEY");
    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080/api/")
                .setBasePath("v3/")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("api_key", API_KEY)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
