package org.fundacionjala.virtualassistant;

import org.fundacionjala.virtualassistant.cassandra.CassandraConfig;
import org.fundacionjala.virtualassistant.cassandra.CassandraConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VirtualAssistantApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VirtualAssistantApplication.class, args);
        CassandraConfig cassandraConfig = context.getBean(CassandraConfig.class);
        CassandraConnection connection = new CassandraConnection(cassandraConfig);
        connection.createSchema();
    }
}
