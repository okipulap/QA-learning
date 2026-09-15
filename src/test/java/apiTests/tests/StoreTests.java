package apiTests.tests;

import apiTests.base.StoreClient;
import apiTests.factories.OrderFactory;
import apiTests.models.store.Order;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@Epic("PetStore API: магазин")
@Owner("Nikita Tkachenko")
public class StoreTests {
	private static StoreClient client;
	private final List<Long> createdOrders = new ArrayList<>();
	private static final Long FAKE_ID = 9999L;
	@BeforeAll
	public static void setUp() {
		client = new StoreClient();
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

		client.postOrder(OrderFactory.randomOrder());

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
		Order request = OrderFactory.randomOrder();

		Order response = client.postOrder(request);
		createdOrders.add(response.getId());

		assertOrderFieldsMatch(request, response);
	}

	@Test
	@Tag("Positive")
	@DisplayName("Получение заказа")
	@Severity(SeverityLevel.BLOCKER)
	@Feature("Ручка API Получения заказа")
	@Story("Юзер получает заказ")
	void getOrderWithStatus200() {
		Order postRequest = OrderFactory.randomOrder();
		Order postResponse = client.postOrder(postRequest);

		Order getResponse = client.getOrderById(postResponse.getId());
		createdOrders.add(getResponse.getId());

		assertOrderFieldsMatch(postRequest, getResponse);
	}

	@Test
	@Tag("Negative")
	@DisplayName("Проверка статуса 404 при ненахождении заказа")
	@Severity(SeverityLevel.NORMAL)
	@Feature("Ручка API выборки заказа")
	@Story("Юзер получает заказ")
	void getOrderTestWithStatus404() {
		Response response = client.getOrderExpected404(FAKE_ID);

		assertEquals("Order not found", response.asString());
	}

	@Test
	@Tag("Positive")
	@DisplayName("Удаление заказа")
	@Severity(SeverityLevel.BLOCKER)
	@Feature("Ручка API Удаление заказа")
	@Story("Юзер удаляет заказ")
	void deleteOrderWithStatus200() {
		Order postRequest = OrderFactory.randomOrder();
		Order postResponse = client.postOrder(postRequest);

		Response delResponse = client.deleteOrder(postResponse.getId());

		assertEquals(HttpStatus.SC_OK, delResponse.getStatusCode());
	}

	@Test
	@Tag("Negative")
	@DisplayName("Удаление несуществующего заказа")
	@Severity(SeverityLevel.CRITICAL)
	@Feature("Ручка API Удаление заказа")
	@Story("Юзер удаляет заказ")
	void deleteOrderExpected404() {
		Response delResponse = client.deleteOrderExpected404(FAKE_ID);

		assertEquals(HttpStatus.SC_NOT_FOUND, delResponse.getStatusCode());
	}

	@AfterEach
	void cleanUp() {
		for (Long orderId : createdOrders) {
			client.deleteOrder(orderId);
		}
		createdOrders.clear();
	}
}
