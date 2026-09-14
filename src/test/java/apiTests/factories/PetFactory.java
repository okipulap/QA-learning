package apiTests.factories;

import apiTests.models.pet.Category;
import apiTests.models.pet.Pet;
import apiTests.models.pet.TagsItem;
import com.github.javafaker.Faker;

import java.util.List;

public class PetFactory {
    private static final Faker FAKER = new Faker();

    private PetFactory() {
    }

    public static Pet createPet(String status) {
        return createPet(FAKER.animal().name(), status);
    }

    public static Pet createPet(String name, String status) {
        return Pet.builder()
                .id(FAKER.number().randomNumber())
                .name(name)
                .category(Category.builder()
                        .id(1L)
                        .name(FAKER.dog().gender())
                        .build())
                .tags(List.of(TagsItem.builder()
                        .id(1L)
                        .name(FAKER.dog().breed())
                        .build()))
                .photoUrls(List.of("https://example.com/photo.jpg"))
                .status(status)
                .build();
    }

    public static Pet updatePet(Long id, String name, String status) {
        return Pet.builder()
                .id(id)
                .name(name)
                .category(Category.builder()
                        .id(1L)
                        .name(FAKER.dog().gender())
                        .build())
                .tags(List.of(TagsItem.builder()
                        .id(1L)
                        .name(FAKER.dog().breed())
                        .build()))
                .photoUrls(List.of("https://example.com/photo.jpg"))
                .status(status)
                .build();
    }

    public static Pet updatePetFromExisting(Long id, String name, String status,
                                           List<String> photoUrls, Category category,
                                           List<TagsItem> tags) {
        return Pet.builder()
                .id(id)
                .name(name)
                .category(category)
                .tags(tags)
                .photoUrls(photoUrls)
                .status(status)
                .build();
    }
}