package apiTests.tests;

import apiTests.base.PetClient;
import apiTests.models.Category;
import apiTests.models.PetRequest;
import apiTests.models.PetResponse;
import apiTests.models.TagsItem;
import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PetStoreTests {
    private static PetClient client;
    private Long petId;

    @Epic("PetStore API")
    @BeforeAll
    public static void setUp() {
        client = new PetClient();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Проверка создания питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API добавления питомца")
    @Story("Юзер создает питомца")
    void createPetTest() {
        PetRequest request = new PetRequest();
        request.setId(ThreadLocalRandom.current().nextLong(1, 1_000_000));
        request.setName("autoPet");
        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");
        request.setCategory(category);
        TagsItem item = new TagsItem();
        item.setId(1);
        item.setName("тестик");
        request.setTags(List.of(item));
        request.setStatus("available");


        PetResponse response = client.createPet(request);
        petId = request.getId();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(request.getId())
                .isEqualTo(response.getId());
        soft.assertThat(request.getName())
                .isEqualTo(response.getName());
        soft.assertThat(request.getCategory())
                .usingRecursiveComparison()
                .isEqualTo(response.getCategory());
        soft.assertThat(request.getTags())
                .usingRecursiveComparison()
                .isEqualTo(response.getTags());
        soft.assertAll();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Проверка выборки питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API выборки питомца")
    @Story("Юзер получает питомца")
    void getPetTestWithStatusCode200() {
        PetRequest request = new PetRequest();
        request.setId(ThreadLocalRandom.current().nextLong(1, 1_000_000));
        request.setName("autoPet");
        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");
        request.setCategory(category);
        TagsItem item = new TagsItem();
        item.setId(1);
        item.setName("тестик");
        request.setTags(List.of(item));
        request.setStatus("available");

        client.createPet(request);
        PetResponse getResponse = client.getByPetId(request.getId());
        petId = request.getId();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(request.getId())
                .isEqualTo(getResponse.getId());
        soft.assertThat(request.getName())
                .isEqualTo(getResponse.getName());
        soft.assertThat(request.getCategory())
                .usingRecursiveComparison()
                .isEqualTo(getResponse.getCategory());
        soft.assertThat(request.getTags())
                .usingRecursiveComparison()
                .isEqualTo(getResponse.getTags());
        soft.assertAll();
    }

    @ParameterizedTest
    @CsvSource( {"pending",
            "sold"}
    )
    @Tag("Positive")
    @DisplayName("Проверка изменения статуса питомца")
    @Owner("Nikita Tkachenko")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API изменения статуса питомца")
    @Story("Юзер изменяет статус питомца")
    void putStatusPetWithStatus200(String status) {
        PetRequest postRequest = new PetRequest();
        postRequest.setId(ThreadLocalRandom.current().nextLong(1, 1_000_000));
        postRequest.setName("autoPet");
        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");
        postRequest.setCategory(category);
        TagsItem item = new TagsItem();
        item.setId(1);
        item.setName("тестик");
        postRequest.setTags(List.of(item));
        postRequest.setStatus("available");

        PetResponse createResponse = client.createPet(postRequest);
        petId = createResponse.getId();

        PetRequest putRequest = new PetRequest();
        putRequest.setId(postRequest.getId());
        putRequest.setName("putPet");
        Category putCategory = new Category();
        putCategory.setId(1);
        putCategory.setName("Dogs");
        putRequest.setCategory(putCategory);
        TagsItem putItem = new TagsItem();
        putItem.setId(1);
        putItem.setName("путовый");
        putRequest.setTags(List.of(putItem));
        putRequest.setStatus(status);

        PetResponse putResponse = client.putPet(putRequest);
        PetResponse getResponse = client.getByPetId(putRequest.getId());

        assertThat(putRequest.getId())
                .isEqualTo(getResponse.getId());
        assertThat(putRequest.getStatus())
                .isEqualTo(getResponse.getStatus());
        assertThat(putRequest.getId())
                .isEqualTo(putResponse.getId());
        assertThat(putRequest.getStatus())
                .isEqualTo(putResponse.getStatus());
    }

    @AfterEach
    void cleanUp() {
        if (petId != null) {
            client.deletePet(petId);
        }
    }
}
