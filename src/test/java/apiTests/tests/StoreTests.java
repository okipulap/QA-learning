package apiTests.tests;

import apiTests.base.StoreClient;
import apiTests.models.store.InventoryResponse;
import apiTests.models.store.OrderRequest;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Epic("PetStore API: магазин")
@Owner("Nikita Tkachenko")
public class StoreTests {
    private static StoreClient client;
    private Long storeId;

    @BeforeAll
    public static void setUp() {
        client = new StoreClient();
    }

    private

    @Test
    @Tag("Positive")
    @DisplayName("Вывод заказов по статусам")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Ручка API получения статусов заказа")
    @Story("Юзер получает количества заказов по статусам")
    void getInventoryTestWithStatus200() {
        InventoryResponse response = client.getInventory();


    }

    @Test
    @Tag("Positive")
    @DisplayName("Создание заказа")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API создания заказа")
    @Story("Юзер создает заказ")
    void postOrderWithStatus200() {

    }

    @AfterEach
    void cleanUp() {
        if (storeId != null) {
            client.deleteOrder(storeId);
        }
    }
}
