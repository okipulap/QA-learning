package apiTests.base;

import apiTests.models.store.InventoryResponse;
import apiTests.models.store.Order;
import apiTests.specs.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StoreClient extends ApiBaseClient {

    private static final String STORE_INVENTORY_ENDPOINT = "/store/inventory";
    private static final String STORE_ENDPOINT = "/store/order";

    public StoreClient() {
        super(RequestSpec.defaultLocalSpec());
    }

    @Step("Вывод количества заказов со статусами")
    public InventoryResponse getInventory() {
        return get(STORE_INVENTORY_ENDPOINT)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(InventoryResponse.class);
    }

    @Step("Создание заказа")
    public Order postOrder(Order request) {
        return post(STORE_ENDPOINT, request)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/order-response-schema.json"))
                .extract()
                .as(Order.class);
    }

    @Step("Поиск заказа по его id: {id}")
    public Order getOrderById(Long id) {
        return getWithPathParam(STORE_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/order-response-schema.json"))
                .extract()
                .as(Order.class);
    }

    @Step("Поиск заказа по несуществующему id")
    public Response getOrderExpected404(Long id) {
        return getWithPathParam(STORE_ENDPOINT, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
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

    @Step("Удаление заказа с несуществующим id")
    public Response deleteOrderExpected404(Long id) {
        return RestAssured.given()
                .spec(RequestSpec.publicSpec())
                .pathParam("id", id)
                .when()
                .delete(STORE_ENDPOINT + "/{id}")
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();
    }
}
