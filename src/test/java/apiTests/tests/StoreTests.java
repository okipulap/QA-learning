package apiTests.tests;

import apiTests.base.StoreClient;
import apiTests.models.pet.Pet;
import apiTests.models.store.InventoryResponse;
import apiTests.models.store.Order;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Epic("PetStore API: магазин")
@Owner("Nikita Tkachenko")
public class StoreTests {
    private static StoreClient client;
    private Long storeId;

    private static final Faker faker = new Faker();
    private static final Long ORDER_ID = faker.number().randomNumber();
    private static final Long PET_ID = faker.number().randomNumber();
    private static final int QUANTITY = faker.number().randomDigitNotZero();
    private static final String SHIP_DATE = OffsetDateTime
            .now(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    private static final String STATUS_APPROVED = "approved";
    private static final boolean COMPLETE_TRUE = true;

    @BeforeAll
    public static void setUp() {
        client = new StoreClient();
    }

    private Order createDefaultOrderRequest() {
        return Order.builder()
                .id(ORDER_ID)
                .petId(PET_ID)
                .quantity(QUANTITY)
                .shipDate(SHIP_DATE)
                .status(STATUS_APPROVED)
                .complete(COMPLETE_TRUE)
                .build();
    }

    private void assertOrderFieldsMatch(Order request, Order response) {
        SoftAssertions soft = new SoftAssertions();

        OffsetDateTime requestTime = OffsetDateTime.parse(request.getShipDate()).truncatedTo(ChronoUnit.MILLIS);
        OffsetDateTime responseTime = OffsetDateTime.parse(response.getShipDate()).truncatedTo(ChronoUnit.MILLIS);

        soft.assertThat(request.getId()).isEqualTo(response.getId());
        soft.assertThat(request.getPetId()).isEqualTo(response.getPetId());
        soft.assertThat(request.getQuantity()).isEqualTo(response.getQuantity());
        soft.assertThat(requestTime).isEqualTo(responseTime);
        soft.assertThat(request.getStatus()).isEqualTo(response.getStatus());
        soft.assertThat(request.isComplete()).isEqualTo(response.isComplete());
        soft.assertAll();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Показ роста счетчика после создания заказа")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Ручка API получения статусов заказа")
    @Story("Юзер получает количества заказов по статусам")
    void getInventoryTestWithStatus200() {
        int approvedBefore = client.getInventory().getApproved();

        client.postOrder(createDefaultOrderRequest());

        int approvedAfter = client.getInventory().getApproved();

        assertThat(approvedAfter).isGreaterThan(approvedBefore);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Создание заказа")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API создания заказа")
    @Story("Юзер создает заказ")
    void postOrderWithStatus200() {
        Order request = createDefaultOrderRequest();

        Order response = client.postOrder(request);
        storeId = response.getId();

        assertOrderFieldsMatch(request, response);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Получение заказа")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API Получения заказа")
    @Story("Юзер получает заказ")
    void getOrderWithStatus200() {
        Order postRequest = createDefaultOrderRequest();
        Order postResponse = client.postOrder(postRequest);

        Order getResponse = client.getOrderById(postResponse.getId());
        storeId = getResponse.getId();

        assertOrderFieldsMatch(postRequest, getResponse);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Проверка статуса 404 при ненахождении заказа")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Ручка API выборки заказа")
    @Story("Юзер получает заказ")
    void getOrderTestWithStatus404() {
        Response response = client.getOrderExpected404(faker.number().randomNumber());

        assertEquals("Order not found", response.asString());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Удаление заказа")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API Удаление заказа")
    @Story("Юзер удаляет заказ")
    void deleteOrderWithStatus200() {
        Order postRequest = createDefaultOrderRequest();
        Order postResponse = client.postOrder(postRequest);

        Response delResponse = client.deleteOrder(postResponse.getId());

        assertEquals(HttpStatus.SC_OK, delResponse.getStatusCode());
    }

    @AfterEach
    void cleanUp() {
        if (storeId != null) {
            client.deleteOrder(storeId);
        }
    }
}
