package com.louishoughton.mealplanner.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Testcontainers
class DynamoDbUserRepositoryTest {

    @Container
    public GenericContainer dynamoDBLocal = new GenericContainer(DockerImageName.parse("amazon/dynamodb-local:1.11.477"))
            .withExposedPorts(8000);

    private static DynamoDbEnhancedClient dynamoDbClient;
    private DynamoDbTable<User> userTable;
    private DynamoDbUserRepository underTest;

    @BeforeEach
    void setup() throws URISyntaxException {
        String dynamoUri = "http://" + dynamoDBLocal.getContainerIpAddress() + ":" + dynamoDBLocal.getMappedPort(8000);

        dynamoDbClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(
                        DynamoDbClient.builder()
                                .endpointOverride(new URI(dynamoUri))
                                .build())
                .build();

        userTable = dynamoDbClient.table("User", DynamoDbUserRepository.USER_TABLE_SCHEMA);
        userTable.createTable();
        underTest = new DynamoDbUserRepository(dynamoDbClient, userTable.tableName());
    }

    @AfterEach
    void tearDown() {
        dynamoDbClient.table(userTable.tableName(), DynamoDbUserRepository.USER_TABLE_SCHEMA).deleteTable();
    }

    @Test
    void should_save_new_user_and_load() {
        User user = new User();
        user.setGuid(UUID.randomUUID().toString());
        user.setName("Louis");

        underTest.save(user);

        underTest.findByGuid(user.getGuid())
                .ifPresentOrElse(
                        u -> assertThat(u.getGuid()).isEqualTo(user.getGuid()),
                        () -> fail("User not saved"));
    }

    @Test
    void should_save_meals_list_for_user() {
        User user = new User();
        user.setGuid(UUID.randomUUID().toString());
        user.setName("Louis");
        Meal meal = new Meal();
        meal.setName("Burritos");
        user.setMeals(List.of(meal));
        user.setCreatedAt(Instant.now());

        underTest.save(user);

        underTest.findByGuid(user.getGuid())
                .ifPresentOrElse(
                        u -> assertThat(u.getMeals()).containsExactly(meal),
                        () -> fail("Meals not saved"));
    }

    @Test
    void should_update_meals_list_for_user() {
        User user = new User();
        user.setGuid(UUID.randomUUID().toString());
        user.setName("Louis");
        Meal meal = new Meal();
        meal.setName("Burritos");
        user.setMeals(List.of(meal));
        user.setCreatedAt(Instant.now());

        underTest.save(user);

        underTest.findByGuid(user.getGuid())
                .ifPresentOrElse(
                        u -> assertThat(u.getMeals()).containsExactly(meal),
                        () -> fail("Meals not saved"));

        Meal anotherMeal = new Meal();
        anotherMeal.setName("Beans on Toast");

        user.setMeals(List.of(meal, anotherMeal));

        underTest.update(user);

        underTest.findByGuid(user.getGuid())
                .ifPresentOrElse(
                        u -> assertThat(u.getMeals()).containsExactly(meal, anotherMeal),
                        () -> fail("Meals not updated"));
    }
}
