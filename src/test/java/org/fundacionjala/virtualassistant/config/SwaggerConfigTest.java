package org.fundacionjala.virtualassistant.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(properties = {
        "POSTGRES_USER=postgres",
        "POSTGRES_PASSWORD=password",
        "MONGO_INITDB_ROOT_USERNAME=VirtualAssistant60089",
        "MONGO_INITDB_ROOT_PASSWORD=rGYwVKko8ihLmyaC"
})
@AutoConfigureMockMvc
class SwaggerConfigTest {

    @Autowired
    private MockMvc mockMvc;

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
