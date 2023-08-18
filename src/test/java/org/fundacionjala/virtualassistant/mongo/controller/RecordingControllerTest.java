package org.fundacionjala.virtualassistant.mongo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecordingController.class)
@AutoConfigureMockMvc
class RecordingControllerTest {

    private final static String END_POINT_PATH="/recordings";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private RecordingService recordingServ;

    @Test
    void givenRecordingsEndpoint_whenGetRequest_thenResponseStatusIsOk() throws Exception {
        mockMvc.perform(get("/recordings"))
                .andExpect(status().isOk());
    }

    @Test
    void givenAudioEndpoint_whenGetRequestWithUserIdAndChatId_thenResponseStatusIsOk() throws Exception {
        Long userId = 123L;
        Long chatId = 456L;
        mockMvc.perform(get(END_POINT_PATH + "/audio/")
                        .param("idUser", userId.toString())
                        .param("idChat", chatId.toString()))
                .andExpect(status().isOk());
    }

}