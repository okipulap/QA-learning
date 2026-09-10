package apiTests.tests;

import apiTests.base.UserClient;
import apiTests.models.user.User;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

@Epic("PetStore API: магазин")
@Owner("Nikita Tkachenko")
public class UserTests {

    private static UserClient client;
    private String userName;

    private static final Faker faker = new Faker();

    private static final Long USER_ID = faker.number().randomNumber();
    private static final String USER_NAME = faker.funnyName().name();
    private static final String FIRST_NAME = faker.name().firstName();
    private static final String LAST_NAME = faker.name().lastName();
    private static final String USER_EMAIL = faker.internet().emailAddress();
    private static final String USER_PASS = faker.internet().password();
    private static final String USER_PHONE = faker.phoneNumber().cellPhone();
    private static final int USER_STATUS = 1;

    @BeforeAll
    public static void setUp() {
        client = new UserClient();
    }

    private User createDefaultUserRequest() {
        return User.builder()
                .id(USER_ID)
                .username(USER_NAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(USER_EMAIL)
                .password(USER_PASS)
                .phone(USER_PHONE)
                .userStatus(USER_STATUS)
                .build();
    }

    private void assertUserFieldsMatch(User request, User response) {
        SoftAssertions soft = new SoftAssertions();

        soft.assertThat(request.getId()).isEqualTo(response.getId());
        soft.assertThat(request.getUsername()).isEqualTo(response.getUsername());
        soft.assertThat(request.getFirstName()).isEqualTo(response.getFirstName());
        soft.assertThat(request.getLastName()).isEqualTo(response.getLastName());
        soft.assertThat(request.getEmail()).isEqualTo(response.getEmail());
        soft.assertThat(request.getPassword()).isEqualTo(response.getPassword());
        soft.assertThat(request.getPhone()).isEqualTo(response.getPhone());
        soft.assertThat(request.getUserStatus()).isEqualTo(response.getUserStatus());
        soft.assertAll();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Создание юзера")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API создания юзера")
    @Story("Юзер создает юзера")
    void postUserWithStatus200() {
        User request = createDefaultUserRequest();

        User response = client.postUser(request);
        userName = response.getUsername();

        assertUserFieldsMatch(request, response);
    }

    @AfterEach
    void cleanUp() {
        if (userName != null) {
            client.deleteUser(userName);
        }
    }
}
