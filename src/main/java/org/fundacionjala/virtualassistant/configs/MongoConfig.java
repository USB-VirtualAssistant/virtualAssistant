package org.fundacionjala.virtualassistant.configs;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "recordingsDB";
    }

    @Override
    public MongoClient mongoClient() {
        String connectionString = "mongodb://VirtualAssistant60089:rGYwVKko8ihLmyaC@localhost:27017/recordingsDB?authSource=admin";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();
        return MongoClients.create(settings);
    }
}
