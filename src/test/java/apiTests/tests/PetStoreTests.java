package apiTests.tests;

import apiTests.base.PetClient;
import apiTests.models.Category;
import apiTests.models.PetRequest;
import apiTests.models.PetResponse;
import apiTests.models.TagsItem;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class PetStoreTests {
    private static PetClient client;
    private Long petId;

    private static final String PET_NAME = "autoPet";
    private static final String STATUS_AVAILABLE = "available";
    private static final Integer CATEGORY_ID = 1;
    private static final String CATEGORY_NAME = "Dogs";
    private static final Integer TAG_ID = 1;
    private static final String TAG_NAME = "тестик";

    @Epic("PetStore API: питомцы")
    @BeforeAll
    public static void setUp() {
        client = new PetClient();
    }

    private PetRequest createDefaultPetRequest() {
        return createPetRequestWithStatus(STATUS_AVAILABLE);
    }

    private PetRequest createPetRequestWithStatus(String status) {
        return PetRequest.builder()
                .id(ThreadLocalRandom.current().nextLong(1, 1_000_000))
                .name(PET_NAME)
                .category(Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build())
                .tags(List.of(TagsItem.builder().id(TAG_ID).name(TAG_NAME).build()))
                .status(status)
                .build();
    }

    private void assertPetFieldsMatch(PetRequest request, PetResponse response) {
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(request.getId()).isEqualTo(response.getId());
        soft.assertThat(request.getName()).isEqualTo(response.getName());
        soft.assertThat(request.getCategory()).usingRecursiveComparison().isEqualTo(response.getCategory());
        soft.assertThat(request.getTags()).usingRecursiveComparison().isEqualTo(response.getTags());
        soft.assertAll();
    }

    private static Stream<Arguments> invalidPetRequests() {
        return Stream.of(
                Arguments.of("Битый JSON (id без значения)",
                        """
                        {
                          "id": ,
                          "name": "doggie",
                          "category": {
                            "id": 1,
                            "name": "Dogs"
                          },
                          "tags": [
                            {
                              "id": 0,
                              "name": "string"
                            }
                          ],
                          "status": "available"
                        }
                        """),
                Arguments.of("Битый JSON (нет закрывающей скобки)",
                        """
                        {
                          "id": 123,
                          "name": "doggie"
                        """)
        );
    }

    @Test
    @Tag("Positive")
    @DisplayName("Проверка создания питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API добавления питомца")
    @Story("Юзер создает питомца")
    void createPetTest() {
        PetRequest request = createDefaultPetRequest();

        PetResponse response = client.createPet(request);
        petId = response.getId();

        assertPetFieldsMatch(request, response);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Проверка создания питомца с помощью формы")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API изменения питомца с помощью формы")
    @Story("Юзер создает питомца")
    void updateWithFormDataTest() {
        PetRequest postRequest = createDefaultPetRequest();
        PetResponse postResponse = client.createPet(postRequest);

        petId = postResponse.getId();

        PetRequest formDataRequest = new PetRequest();
        formDataRequest.setId(postRequest.getId());
        formDataRequest.setName("form data name");
        formDataRequest.setStatus("pending");

        Response formDataResponse = client.updatePetWithFormData(
                formDataRequest, formDataRequest.getId(),
                formDataRequest.getName(), formDataRequest.getStatus());

        assertThat(HttpStatus.SC_OK)
                .isEqualTo(formDataResponse.statusCode());
    }

    @ParameterizedTest
    @MethodSource("invalidPetRequests")
    @Tag("Negative")
    @DisplayName("Проверка создания питомца с невалидными id, name")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Ручка API добавления питомца")
    @Story("Юзер создает питомца")
    void createPetWithStatus400(String description, String brokenJson) {
        Response response = client.createPetWithBrokenJson(brokenJson);

        assertEquals("Input error: unable to convert input to io.swagger.petstore.model.Pet",
                response.jsonPath().getString("message"));
    }

    @Test
    @Tag("Positive")
    @DisplayName("Проверка выборки питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API выборки питомца")
    @Story("Юзер получает питомца")
    void getPetTestWithStatusCode200() {
        PetRequest request = createDefaultPetRequest();

        client.createPet(request);
        PetResponse getResponse = client.getByPetId(request.getId());
        petId = getResponse.getId();

        assertPetFieldsMatch(request, getResponse);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Проверка статуса 404 при ненахождении питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Ручка API выборки питомца")
    @Story("Юзер получает питомца")
    void getPetWithStatus404() {

        Response response = client.getPetExpected404(ThreadLocalRandom.current()
                .nextLong(1_000_000_000L, Long.MAX_VALUE));

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode());
        assertEquals("Pet not found", response.asString());
    }

    @ParameterizedTest
    @CsvSource({"pending", "sold"})
    @Tag("Positive")
    @DisplayName("Проверка изменения статуса питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API изменения статуса питомца")
    @Story("Юзер изменяет статус питомца")
    void putPetWithStatus200(String status) {
        PetRequest postRequest = createDefaultPetRequest();

        PetResponse createResponse = client.createPet(postRequest);
        petId = createResponse.getId();

        PetRequest putRequest = PetRequest.builder()
                .id(postRequest.getId())
                .name("putPet")
                .category(Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build())
                .tags(List.of(TagsItem.builder().id(TAG_ID).name("путовый").build()))
                .status(status)
                .build();

        PetResponse putResponse = client.putPet(putRequest);
        PetResponse getResponse = client.getByPetId(putRequest.getId());

        assertPetFieldsMatch(putRequest, putResponse);
        assertPetFieldsMatch(putRequest, getResponse);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Проверка статуса 404 при попытке изменения питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Ручка API изменения питомца")
    @Story("Юзер изменяет статус питомца")
    void putPetWithStatus404() {
        Long fakeId = 9999L;

        PetRequest request = PetRequest.builder()
                .id(fakeId)
                .name("Error 404")
                .category(Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build())
                .tags(List.of((TagsItem.builder().id(TAG_ID).name("пут метод").build())))
                .status("available")
                .build();

        Response response = client.putPetExpected404(request);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode());
        assertEquals("Pet not found", response.asString());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Проверка удаления питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API удаления питомца")
    @Story("Юзер удаляет питомца")
    void deletePetWithStatus200() {
        PetRequest postRequest = createDefaultPetRequest();

        client.createPet(postRequest);

        client.deletePet(postRequest.getId());

        Response getResponse = client.getPetExpected404(postRequest.getId());

        assertEquals(HttpStatus.SC_NOT_FOUND, getResponse.getStatusCode());
        assertEquals("Pet not found", getResponse.asString());
    }

//    @Test
//    @Tag("Negative")
//    @DisplayName("Проверка удаления питомца")
//    @Owner("Nikita Tkachenko")
//    @Severity(SeverityLevel.BLOCKER)
//    @Feature("Ручка API удаления питомца")
//    @Story("Юзер удаляет питомца")

    @AfterEach
    void cleanUp() {
        if (petId != null) {
            client.deletePet(petId);
        }
    }
}
