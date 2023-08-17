package org.fundacionjala.virtualassistant.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void test() throws Exception {
        mockMvc.perform(get("/swagger-ui/"))
                .andExpect(status().isOk());
    }
}