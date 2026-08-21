package apiTests.base;

import apiTests.models.PetRequest;
import apiTests.models.PetResponse;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
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

    @Step("Изменение питомца")
    public PetResponse putPet(PetRequest request, Long id) {
        return put(PET_ENDPOINT, request, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PetResponse.class);
    }

    @Step("Получение питомца")
    public PetResponse getByPetId(Long id) {
        return getById(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PetResponse.class);
    }

    @Step("Удаление питомца")
    public void deletePet(Long id) {
        delete(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK);
    }

}
