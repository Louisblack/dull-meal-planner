package com.louishoughton.mealplanner;

import com.louishoughton.mealplanner.model.DynamoDbUserRepository;
import com.louishoughton.mealplanner.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class TestConfiguration {

    @Bean("dynamoDBLocal")
    public GenericContainer dynamoDbContainer() {
        return new GenericContainer<>(DockerImageName.parse("amazon/dynamodb-local:1.11.477")).withExposedPorts(8000);
    }

    @Bean
    @Primary
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(GenericContainer dynamoDBLocal, @Value("${users.table.name}") String tableName) throws URISyntaxException {
        String dynamoUri = "http://" + dynamoDBLocal.getContainerIpAddress() + ":" + dynamoDBLocal.getMappedPort(8000);

        DynamoDbEnhancedClient dynamoDbClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(
                        DynamoDbClient.builder()
                                .endpointOverride(new URI(dynamoUri))
                                .build())
                .build();

        DynamoDbTable<User> userTable = dynamoDbClient.table(tableName, DynamoDbUserRepository.USER_TABLE_SCHEMA);
        userTable.createTable();
        return dynamoDbClient;
    }
}
