package org.fundacionjala.virtualassistant;

import org.fundacionjala.virtualassistant.cassandra.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VirtualAssistantApplication {
    public static void main(String[] args) {
        SpringApplication.run(VirtualAssistantApplication.class, args);
        Connection connection = new Connection();
        connection.createSchema();
    }
}
