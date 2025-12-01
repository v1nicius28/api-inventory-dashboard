package dashboard;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestConfiguration
@Testcontainers
public class TestConfig {

    @Bean
    @Primary
    public MongoClient mongoClient(MongoDBContainer mongo) {
        return MongoClients.create(mongo.getReplicaSetUrl());
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient client) {
        return new MongoTemplate(client, "test");
    }

    @Bean
    @Primary
    public MongoDBContainer mongoContainer() {
        MongoDBContainer mongo = new MongoDBContainer("mongo:7");
        mongo.start();
        return mongo;
    }
}
