package apiTests.base;

import apiTests.models.PetRequest;
import apiTests.models.PetResponse;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
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

    @Step("Создание питомца с ошибкой 405")
    public Response createPetExpected405(PetRequest request) {
        return create(PET_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .extract().response();
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

    @Step("Получение питомца по его id: {id}")
    public PetResponse getByPetId(Long id) {
        return getById(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PetResponse.class);
    }

    @Step("Удаление питомца по его id: {id}")
    public void deletePet(Long id) {
        delete(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK);
    }

}
