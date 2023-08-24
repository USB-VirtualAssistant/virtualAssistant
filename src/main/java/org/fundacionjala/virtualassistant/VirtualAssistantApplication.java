package org.fundacionjala.virtualassistant;

import org.fundacionjala.virtualassistant.cassandra.CassandraConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class VirtualAssistantApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(VirtualAssistantApplication.class, args);
        CassandraConnection cassandraConnection = new CassandraConnection();
        cassandraConnection.createSchema();
    }
}
