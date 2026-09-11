package apiTests.tests;

import apiTests.base.UserClient;
import apiTests.models.user.User;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


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
    private static final String USER_PHONE = faker.phoneNumber().phoneNumber();
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

    @Test
    @Tag("Positive")
    @DisplayName("Получение юзера")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API создания юзера")
    @Story("Юзер получает юзера")
    void getUserWithStatus200() {
        User request = createDefaultUserRequest();

        User response = client.postUser(request);
        User getResponse = client.getUserByUsername(response.getUsername());

        userName = response.getUsername();

        assertNotNull(getResponse);
        assertUserFieldsMatch(request, getResponse);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Получение юзера по несуществующему username")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API создания юзера")
    @Story("Юзер получает юзера")
    void getUserExpected404() {
        String fakeUserName = "fakeGetUser";

        Response getResponse = client.getUserExpected404(fakeUserName);


        assertEquals("User not found", getResponse.asString());
    }

//    @Test
//    @Tag("Positive")
//    @DisplayName("Изменение юзера")
//    @Severity(SeverityLevel.BLOCKER)
//    @Feature("Ручка API изменения юзера")
//    @Story("Юзер изменяет юзера")
//    void putUserWithStatus200() {
//        User postRequest = createDefaultUserRequest();
//        User postResponse = client.postUser(postRequest);
//
//        User putRequest = User.builder()
//                .id(123L)
//                .username(postResponse.getUsername())
//                .firstName("test first name")
//                .lastName("test last name")
//                .email("test@gmail.com")
//                .password("testPass")
//                .phone("+79999999999")
//                .userStatus(2)
//                .build();
//
//        User putResponse = client.putUserByUsername(postRequest.getUsername());
//        User getResponse = client.getUserByUsername(putRequest.getUsername());
//
//        userName = getResponse.getUsername();
//
//        assertNotNull(putResponse);
//        assertUserFieldsMatch(putRequest, putResponse);
//        assertUserFieldsMatch(putRequest, getResponse);
//    }

    @Test
    @Tag("Positive")
    @DisplayName("Тест удаления юзера")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API удаления юзера")
    @Story("Юзер удаляет юзера")
    void deleteUserWithStatus200() {
        User postRequest = createDefaultUserRequest();

        User postResponse = client.postUser(postRequest);

        Response delResponse = client.deleteUser(postResponse.getUsername());

        assertEquals(HttpStatus.SC_OK, delResponse.getStatusCode());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Тест удаления юзера с несуществующим username")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API удаления юзера")
    @Story("Юзер удаляет юзера")
    void deleteUserExpected404() {
        String fakeUserName = "testUserName";

        Response delResponse = client.deleteOrderExpected404(fakeUserName);

        assertEquals(HttpStatus.SC_NOT_FOUND, delResponse.getStatusCode());
    }

    @AfterEach
    void cleanUp() {
        if (userName != null) {
            client.deleteUser(userName);
        }
    }
}
