package apiTests.tests;

import apiTests.base.UserClient;
import apiTests.models.user.User;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("PetStore API: магазин")
@Owner("Nikita Tkachenko")
public class UserTests {

    private static UserClient client;
    private final List<String> createdUsernames = new ArrayList<>();

    private static final Faker faker = new Faker();

    @BeforeAll
    public static void setUp() {
        client = new UserClient();
    }

    private User buildRandomUser() {
        return User.builder()
                .id(faker.number().randomNumber())
                .username(faker.funnyName().name() + faker.number().digits(4))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().phoneNumber())
                .userStatus(1)
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
        User request = buildRandomUser();

        User response = client.postUser(request);

        createdUsernames.add(response.getUsername());

        assertUserFieldsMatch(request, response);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Получение юзера")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API создания юзера")
    @Story("Юзер получает юзера")
    void getUserWithStatus200() {
        User request = buildRandomUser();

        User response = client.postUser(request);
        User getResponse = client.getUserByUsername(response.getUsername());

        createdUsernames.add(getResponse.getUsername());

        assertNotNull(getResponse);
        assertUserFieldsMatch(request, getResponse);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Создание списка юзеров")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API создания списка юзеров")
    @Story("Юзер создает список юзеров")
    void postUserWithListStatus200() {
        User firstUser = buildRandomUser();
        User secondUser = buildRandomUser();
        List<User> request = List.of(firstUser, secondUser);
        createdUsernames.add(firstUser.getUsername());
        createdUsernames.add(secondUser.getUsername());

        List<User> response = client.postUsersWithList(request);

        assertNotNull(response);
        assertEquals(2, response.size());

        User getFirst = client.getUserByUsername(firstUser.getUsername());
        User getSecond = client.getUserByUsername(secondUser.getUsername());

        assertEquals(firstUser.getUsername(), getFirst.getUsername());
        assertEquals(firstUser.getEmail(), getFirst.getEmail());

        assertEquals(secondUser.getUsername(), getSecond.getUsername());
        assertEquals(secondUser.getEmail(), getSecond.getEmail());

    }

    @Test
    @Tag("Negative")
    @DisplayName("Получение юзера по несуществующему username")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Ручка API создания юзера")
    @Story("Юзер получает юзера")
    void getUserExpected404() {
        String fakeUserName = "fakeGetUser";

        Response getResponse = client.getUserExpected404(fakeUserName);


        assertEquals("User not found", getResponse.asString());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Изменение юзера по его username")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API изменения юзера")
    @Story("Юзер изменяет юзера")
    void putUserWithStatus200() {
        User postRequest = buildRandomUser();
        client.postUser(postRequest);

        User putRequest = User.builder()
                .id(postRequest.getId())
                .username(postRequest.getUsername())
                .firstName("Test")
                .lastName("Testovich")
                .email("test@email.com")
                .password("testPass")
                .phone("+79999999999")
                .userStatus(5)
                .build();
        User putResponse = client.putUserByUsername(putRequest, postRequest.getUsername());

        User getResponse = client.getUserByUsername(putResponse.getUsername());

        createdUsernames.add(putResponse.getUsername());

        assertNotNull(putResponse);
        assertUserFieldsMatch(putRequest, putResponse);
        assertUserFieldsMatch(putRequest, getResponse);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Изменение юзера по несуществующему username")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Ручка API изменения юзера")
    @Story("Юзер изменяет несуществующего юзера")
    void putUserExpected404() {
        String fakeUsername = "tesUsername1234";

        User putRequest = User.builder()
                .id(1L)
                .username(fakeUsername)
                .firstName("Test")
                .lastName("Testovich")
                .email("test@email.com")
                .password("testPass")
                .phone("+79999999999")
                .userStatus(5)
                .build();

        Response putResponse = client.putUserByUsernameExpected404(putRequest, fakeUsername);

        assertEquals("User not found", putResponse.asString());
    }


    @Test
    @Tag("Positive")
    @DisplayName("Тест удаления юзера")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Ручка API удаления юзера")
    @Story("Юзер удаляет юзера")
    void deleteUserWithStatus200() {
        User postRequest = buildRandomUser();

        User postResponse = client.postUser(postRequest);

        Response delResponse = client.deleteUser(postResponse.getUsername());

        assertEquals(HttpStatus.SC_OK, delResponse.getStatusCode());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Тест удаления юзера с несуществующим username")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Ручка API удаления юзера")
    @Story("Юзер удаляет юзера")
    void deleteUserExpected404() {
        String fakeUserName = "testUserName";

        Response delResponse = client.deleteOrderExpected404(fakeUserName);

        assertEquals(HttpStatus.SC_NOT_FOUND, delResponse.getStatusCode());
    }

    @AfterEach
    void cleanUp() {
        for (String username : createdUsernames) {
            client.deleteUser(username);
        }
        createdUsernames.clear();
    }
}
