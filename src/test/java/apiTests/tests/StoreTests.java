package apiTests.tests;

import apiTests.base.StoreClient;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class StoreTests {
    private static StoreClient client;
    private Long storeId;

    @Epic("PetStore API: магазин")
    @BeforeAll
    public static void setUp() {
        client = new StoreClient();
    }

    @AfterEach
    void cleanUp() {
        if (storeId != null) {
            client.deleteOrder(storeId);
        }
    }
}
