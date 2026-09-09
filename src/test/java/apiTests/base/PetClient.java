package apiTests.base;

import apiTests.models.ApiResponse;
import apiTests.models.pet.Pet;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PetClient extends ApiBaseClient {

    private static final String PET_ENDPOINT = "/pet";

    public PetClient() {
        super(RequestSpec.defaultLocalSpec());
    }

    @Step("Создание питомца")
    public Pet createPet(Pet request) {
        return post(PET_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/pet-response-schema.json"))
                .extract()
                .as(Pet.class);
    }

    @Step("Загрузка изображения питомцу")
    public ApiResponse uploadPetImage(Long id, File image) {
        return RestAssured.given()
                .spec(RequestSpec.uploadImageSpec())
                .pathParam("petId", id)
                .multiPart("file", image)
                .multiPart("additionalMetadata", "test metadata")
                .when()
                .post(PET_ENDPOINT + "/{petId}/uploadImage")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ApiResponse.class);
    }

    @Step("Изменение питомца с помощью формы")
    public Pet updatePetWithFormData(Long id, String name, String status) {
        return RestAssured.given()
                .spec(RequestSpec.formDataSpec())
                .pathParam("id", id)
                .queryParam("name", name)
                .queryParam("status", status)
                .when()
                .post(PET_ENDPOINT + "/{id}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Pet.class);
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
    public Pet putPet(Pet request) {
        return put(PET_ENDPOINT, request)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/pet-response-schema.json"))
                .extract()
                .as(Pet.class);
    }

    @Step("Изменение питомца с ошибкой 404")
    public Response putPetExpected404(Pet request) {
        return put(PET_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }

    @Step("Получение питомца по его id: {id}")
    public Pet getPetById(Long id) {
        return get(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/pet-response-schema.json"))
                .extract()
                .as(Pet.class);
    }

    @Step("Получение питомцев по статусу: {status}")
    public List<Pet> getPetByStatus(String status) {
        return get(PET_ENDPOINT + "/findByStatus", Map.of("status", status))
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(new TypeRef<List<Pet>>() {
                });
    }

    @Step("Получение питомцев по тегу: {tags}")
    public List<Pet> getPetByTags(String tags) {
        return get(PET_ENDPOINT + "/findByTags", Map.of("tags", tags))
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(new TypeRef<List<Pet>>() {
                });
    }

    @Step("Получение питомца с ошибкой 404")
    public Response getPetExpected404(Long id) {
        return get(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }

    @Step("Удаление питомца по его id: {id}")
    public Response deletePet(Long id) {
        return delete(PET_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }

    @Step("Удаление питомца с несуществующим id")
    public Response deletePetExpected404(Long id) {
        return RestAssured.given()
                .spec(RequestSpec.publicSpec())
                .pathParam("id", id)
                .when()
                .delete(PET_ENDPOINT + "/{id}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }
}