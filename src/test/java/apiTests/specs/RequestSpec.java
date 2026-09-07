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

    private static final Dotenv DOTENV = Dotenv.load();

    private static final String API_KEY = DOTENV.get("PETSTORE_API_KEY");
    private static final String BASE_URI = DOTENV.get("PETSTORE_BASE_URI");
    private static final String BASE_PATH = DOTENV.get("PETSTORE_BASE_PATH");

    @Step("Создание дефолтной спецификации запроса")
    public static RequestSpecification defaultSpec() {
        return baseSpec(new RequestSpecBuilder()
                .setContentType(ContentType.JSON));
    }

    @Step("Создание спецификации запроса для form data")
    public static RequestSpecification formDataSpec() {
        return baseSpec(new RequestSpecBuilder()
                .setContentType(ContentType.URLENC));
    }

//    @Step("Создание спецификации запроса для запроса загрузки изображения")
//    public static RequestSpecification uploadImageSpec() {
//        return baseSpec(new RequestSpecBuilder()
//                .setContentType(ContentType.BINARY));
//    }

    private static RequestSpecification baseSpec(RequestSpecBuilder builder) {
        return builder
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .setAccept(ContentType.JSON)
                .addHeader("api_key", API_KEY)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
