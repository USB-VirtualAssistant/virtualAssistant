package org.fundacionjala.virtualassistant.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        String postgresUser = System.getenv("POSTGRES_USER");
        String postgresPassword = System.getenv("POSTGRES_PASSWORD");
        String mongoUsername = System.getenv("MONGO_INITDB_ROOT_USERNAME");
        String mongPassword = System.getenv("MONGO_INITDB_ROOT_PASSWORD");

        System.setProperty("POSTGRES_USER", postgresUser);
        System.setProperty("POSTGRES_PASSWORD", postgresPassword);
        System.setProperty("MONGO_INITDB_ROOT_USERNAME", mongoUsername);
        System.setProperty("MONGO_INITDB_ROOT_PASSWORD", mongPassword);
    }

    @Test
    void givenSwaggerUIEndpoint_whenGetRequest_thenResponseStatusIsOk() throws Exception {
        mockMvc.perform(get("/swagger-ui/"))
                .andExpect(status().isOk());
    }

    @Test
    void givenSwaggerResourcesEndpoint_whenGetRequest_thenResponseStatusIsOkAndContentTypeIsJSON() throws Exception {
        mockMvc.perform(get("/swagger-resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("default"));
    }

    @Test
    void givenSwaggerAPIDocsEndpoint_whenGetRequest_thenResponseStatusIsOk() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }
}
