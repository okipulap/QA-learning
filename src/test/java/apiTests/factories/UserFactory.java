package apiTests.factories;

import apiTests.models.user.User;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.IntStream;

public class UserFactory {
    private static final Faker faker = new Faker();

    private UserFactory() {
    }

    public static User randomUser() {
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

    public static List<User> listOfUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> randomUser())
                .toList();
    }

    public static User updateUser(Long id, String username) {
        return User.builder()
                .id(id)
                .username(username)
                .firstName("Test")
                .lastName("Testovich")
                .email("test@email.com")
                .password("testPass")
                .phone("+79999999999")
                .userStatus(5)
                .build();
    }
}
