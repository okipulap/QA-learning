package apiTests.factories;

import apiTests.models.store.Order;
import com.github.javafaker.Faker;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OrderFactory {
    private static final Faker FAKER = new Faker();

    private OrderFactory() {
    }

    public static Order randomOrder() {
        return Order.builder()
                .id(FAKER.number().randomNumber())
                .petId(FAKER.number().randomNumber())
                .quantity(FAKER.number().randomDigitNotZero())
                .shipDate(OffsetDateTime
                        .now(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .status("approved")
                .complete(true)
                .build();
    }
}