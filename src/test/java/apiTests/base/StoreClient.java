package apiTests.base;

import apiTests.models.store.InventoryResponse;
import apiTests.models.store.OrderRequest;
import apiTests.models.store.OrderResponse;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StoreClient extends ApiBaseClient {

    private static final String STORE_ENDPOINT = "/store";

    public StoreClient() {
        super(RequestSpec.defaultSpec());
    }

    @Step("Вывод количества заказов со статусами")
    public InventoryResponse getInventory() {
        return get(STORE_ENDPOINT + "/inventory")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(InventoryResponse.class);
    }

    @Step("Создание заказа")
    public OrderResponse postOrder(OrderRequest request) {
        return post(STORE_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/order-response-schema.json"))
                .extract()
                .as(OrderResponse.class);
    }

    @Step("Поиск заказа по его id: {id}")
    public OrderResponse getOrderById(Long id) {
        return get(STORE_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/order-response-schema.json"))
                .extract()
                .as(OrderResponse.class);
    }

    @Step("Удаление заказа по id: {id}")
    public Response deleteOrder(Long id) {
        return delete(STORE_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }
}
