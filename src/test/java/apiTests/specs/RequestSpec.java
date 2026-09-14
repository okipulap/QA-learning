package apiTests.specs;

import apiTests.config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
	private static final String API_KEY = Config.get("PETSTORE_API_KEY", "");

	private static final String BASE_URI =
		Config.get("PETSTORE_BASE_URI", "http://localhost:8080/api/");
	private static final String BASE_PATH =
		Config.get("PETSTORE_BASE_PATH", "v3/");

	private static final String BASE_PUBLIC_URI =
		Config.get("PETSTORE_BASE_PUBLIC_URI", "https://petstore.swagger.io/");
	private static final String BASE_PUBLIC_PATH =
		Config.get("PETSTORE_BASE_PUBLIC_PATH", "v2/");

	public static RequestSpecification defaultLocalSpec() {
		return baseSpec(new RequestSpecBuilder()
			.setBaseUri(BASE_URI)
			.setBasePath(BASE_PATH)
			.setAccept(ContentType.JSON)
			.setContentType(ContentType.JSON));
	}

	public static RequestSpecification publicSpec() {
		return baseSpec(new RequestSpecBuilder()
			.setBaseUri(BASE_PUBLIC_URI)
			.setBasePath(BASE_PUBLIC_PATH)
			.setAccept(ContentType.JSON)
			.setContentType(ContentType.JSON));
	}

	public static RequestSpecification formDataSpec() {
		return baseSpec(new RequestSpecBuilder()
			.setBaseUri(BASE_URI)
			.setBasePath(BASE_PATH)
			.setAccept(ContentType.JSON)
			.setContentType(ContentType.URLENC));
	}

	public static RequestSpecification uploadImageSpec() {
		return baseSpec(new RequestSpecBuilder()
			.setBaseUri(BASE_PUBLIC_URI)
			.setBasePath(BASE_PUBLIC_PATH)
			.setAccept(ContentType.JSON)
			.setContentType(ContentType.MULTIPART));
	}

	private static RequestSpecification baseSpec(RequestSpecBuilder builder) {
		return builder
			.addHeader("api_key", API_KEY)
			.addFilter(new AllureRestAssured())
			.build();
	}
}
