package com.louishoughton.mealplanner.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.time.Instant;
import java.util.Optional;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;

@Repository
public class DynamoDbUserRepository implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbUserRepository.class);

    private final DynamoDbEnhancedClient dynamoDbClient;
    private final String tableName;

    public static final TableSchema<Meal> MEAL_TABLE_SCHEMA =
            StaticTableSchema.builder(Meal.class)
                    .newItemSupplier(Meal::new)
                    .addAttribute(String.class, a -> a.name("name")
                            .getter(Meal::getName)
                            .setter(Meal::setName))
                            .addAttribute(Instant.class, a -> a.name("created_at")
                                    .getter(Meal::getCreatedAt)
                                    .setter(Meal::setCreatedAt))
                            .build();

    public static final TableSchema<User> USER_TABLE_SCHEMA =
            StaticTableSchema.builder(User.class)
                    .newItemSupplier(User::new)
                    .addAttribute(String.class, a -> a.name("guid")
                            .getter(User::getGuid)
                            .setter(User::setGuid)
                            .tags(primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("name")
                            .getter(User::getName)
                            .setter(User::setName))
                    .addAttribute(EnhancedType.listOf(
                            EnhancedType.documentOf(Meal.class, MEAL_TABLE_SCHEMA)), a -> a.name("meals")
                            .getter(User::getMeals)
                            .setter(User::setMeals))
                    .addAttribute(Instant.class, a -> a.name("created_at")
                            .getter(User::getCreatedAt)
                            .setter(User::setCreatedAt))
                    .build();

    public DynamoDbUserRepository(DynamoDbEnhancedClient dynamoDbClient,
                                  @Value("${users.table.name}") String tableName) {
        this.dynamoDbClient = dynamoDbClient;
        this.tableName = tableName;
    }

    public Optional<User> get(String userGuid) {
        User item = dynamoDbClient.table(tableName, USER_TABLE_SCHEMA).getItem(GetItemEnhancedRequest.builder()
                .key(r -> r.partitionValue(userGuid))
                .build());

        return Optional.ofNullable(item);
    }


    public void save(User user) {
        try {
            dynamoDbClient.table(tableName, USER_TABLE_SCHEMA).putItem(user);
        } catch (DynamoDbException e) {
            LOGGER.error("Error saving user", e);
        }
    }

    public void update(User user) {
        try {
            dynamoDbClient.table(tableName, USER_TABLE_SCHEMA).updateItem(user);
        } catch (DynamoDbException e) {
            LOGGER.error("Error saving user", e);
        }
    }
}
