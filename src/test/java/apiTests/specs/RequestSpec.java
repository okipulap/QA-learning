package apiTests.specs;

import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.*;
import io.restassured.specification.ResponseSpecification;

public class RequestSpec {

    private static final String API_KEY = Dotenv.load().get("PETSTORE_API_KEY");

    @Step("Создание дефолтной спецификации запроса")
    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/")
                .setBasePath("v2/")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("api_key", API_KEY)
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Step("Создание спецификации запроса для form data")
    public static RequestSpecification formDataSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/")
                .setBasePath("v2/")
                .setContentType(ContentType.URLENC)
                .setAccept(ContentType.JSON)
                .addHeader("api_key", API_KEY)
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Step("Создание спецификации запроса для запроса загрузки изображения")
    public static RequestSpecification uploadImageSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/")
                .setBasePath("v2/")
                .setContentType(ContentType.MULTIPART)
                .setAccept(ContentType.JSON)
                .addHeader("api_key", API_KEY)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
