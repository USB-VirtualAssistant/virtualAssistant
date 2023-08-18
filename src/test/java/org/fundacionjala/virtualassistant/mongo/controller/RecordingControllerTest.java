package org.fundacionjala.virtualassistant.mongo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecordingController.class)
@AutoConfigureMockMvc
class RecordingControllerTest {

    private final static String ENDPOINT_PATH = "/recordings";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private RecordingService recordingService;
    private long userId;
    private long chatId;
    private MockMultipartFile audioFile;
    private String recordingId;
    private RecordingResponse recordingResponse;

    @BeforeEach
    void setUp() {
        userId = 1L;
        chatId = 1L;
        audioFile = new MockMultipartFile("test", "test.wav",
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new byte[100]);
        recordingId = "64debc2d04d1fc7bfe27a989";
        recordingResponse = RecordingResponse.builder()
                .idRecording("")
                .idChat(chatId)
                .idUser(userId)
                .build();
    }

    @Test
    void shouldRespondWithStatusOkWhenAGetRequestIsPerformed() throws Exception {
        mockMvc.perform(get("/recordings"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnOkStatusForGetAudioWithUserIdAndChatId() throws Exception {

        mockMvc.perform(get(ENDPOINT_PATH + "/audio/")
                        .param("idUser",  String.valueOf(userId))
                        .param("idChat",  String.valueOf(chatId)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestStatusForGetAudioWithNoUserAndNoIdAndChatId() throws Exception {
        mockMvc.perform(get(ENDPOINT_PATH + "/audio/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestStatusForGetAudioWithUserIdAndNoChatId() throws Exception {
        mockMvc.perform(get(ENDPOINT_PATH + "/audio/")
                        .param("idUser", String.valueOf(userId)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCreatedStatusForRecordingRequestWithUserIdAndChatId() throws Exception {
        mockMvc.perform(multipart("/recordings/")
                        .file(audioFile)
                        .param("idUser", Long.toString(userId))
                        .param("idChat", Long.toString(chatId))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnOkStatusForGetRecordingWithValidId() throws Exception {
        when(recordingService.getRecording(recordingId)).thenReturn(recordingResponse);
        mockMvc.perform(get("/recordings/audio/" + recordingId))
                .andExpect(status().isOk());
    }
}
