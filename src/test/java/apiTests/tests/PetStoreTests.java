package apiTests.tests;

import apiTests.base.PetClient;
import apiTests.models.Category;
import apiTests.models.PetRequest;
import apiTests.models.PetResponse;
import apiTests.models.TagsItem;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import java.util.List;
import java.util.UUID;

public class PetStoreTests {
    private static PetClient client;

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
    @Story("Создание питомца")
    void createPetTest() {
        PetRequest request = new PetRequest();
        request.setId(150);
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

        assertEquals(150, response.getId());
        assertEquals("autoPet", response.getName());
        assertEquals("available", response.getStatus());
    }
}
