package apiTests.base;

import apiTests.models.PetRequest;
import apiTests.models.PetResponse;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class PetClient extends ApiBaseClient {

    private static final String PET_ENDPOINT = "/pet";

    public PetClient() {
        super(RequestSpec.defaultSpec());
    }

    @Step("Создание питомца")
    public PetResponse createPet(PetRequest request) {
        return create(PET_ENDPOINT, request)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PetResponse.class);
    }

    @Step("Создание питомца с некорректным JSON (400)")
    public Response createPetWithBrokenJson(String rawJson) {
        return RestAssured.given()
                .spec(spec)
                .body(rawJson)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();
    }


    @Step("Изменение питомца по его id: {id}")
    public PetResponse putPet(PetRequest request) {
        return put(PET_ENDPOINT, request)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PetResponse.class);
    }

    @Step("Изменение питомца с ошибкой 400")
    public Response putPetExpected400(PetRequest request) {
        return put(PET_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();
    }

    @Step("Изменение питомца с ошибкой 404")
    public Response putPetExpected404(PetRequest request) {
        return put(PET_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }

    @Step("Изменение питомца с ошибкой 405")
    public Response putPetExpected405(PetRequest request) {
        return put(PET_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .extract()
                .response();
    }

    @Step("Получение питомца по его id: {id}")
    public PetResponse getByPetId(Long id) {
        return getById(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PetResponse.class);
    }

//    @Step("Получение питомца с ошибкой 400")
//    public Response getPetExpected400(Long id) {
//        return getById(PET_ENDPOINT, id)
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.SC_BAD_REQUEST)
//                .extract()
//                .response();
//    }

    @Step("Получение питомца с ошибкой 404")
    public Response getPetExpected404(Long id) {
        return getById(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }

    @Step("Удаление питомца по его id: {id}")
    public void deletePet(Long id) {
        delete(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK);
    }

}
