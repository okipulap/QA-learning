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

    @Epic("PetStore API")
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
                Arguments.of("Без status", PetRequest.builder()
                        .id(null)
                        .name(null)
                        .category(null)
                        .tags(null)
                        .status(null)
                        .build()
                ),
                Arguments.of("Без id", PetRequest.builder()
                        .id(3L)
                        .name("autoPet")
                        .category(Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build())
                        .tags(List.of(TagsItem.builder().id(TAG_ID).name(TAG_NAME).build()))
                        .status("available")
                        .build()
                )
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
        petId = request.getId();

        assertPetFieldsMatch(request, response);
    }

    @ParameterizedTest
    @MethodSource("invalidPetRequests")
    @Tag("Negative")
    @DisplayName("Проверка создания питомца с невалидными id, name")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Ручка API добавления питомца")
    @Story("Юзер создает питомца")
    void createPetWithStatus400(String description, PetRequest request) {
        Response response = client.createPetExpected400(request);
        petId = request.getId();

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
        petId = request.getId();

        assertPetFieldsMatch(request, getResponse);
    }

    @ParameterizedTest
    @CsvSource({"pending", "sold"})
    @Tag("Positive")
    @DisplayName("Проверка изменения статуса питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API изменения статуса питомца")
    @Story("Юзер изменяет статус питомца")
    void putStatusPetWithStatus200(String status) {
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

    @AfterEach
    void cleanUp() {
        if (petId != null) {
            client.deletePet(petId);
        }
    }
}
