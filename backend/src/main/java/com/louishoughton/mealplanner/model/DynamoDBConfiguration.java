package com.louishoughton.mealplanner.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import java.net.URISyntaxException;

@Configuration
public class DynamoDBConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() throws URISyntaxException {
        DynamoDbEnhancedClient dynamoDbClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(
                        DynamoDbClient.builder()
                                .build())
                .build();
        return dynamoDbClient;
    }
}
