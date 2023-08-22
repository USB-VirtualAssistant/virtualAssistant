package org.fundacionjala.virtualassistant;

import org.fundacionjala.virtualassistant.cassandra.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class VirtualAssistantApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(VirtualAssistantApplication.class, args);
        Connection connection = new Connection();
        connection.createSchema();
    }
}
