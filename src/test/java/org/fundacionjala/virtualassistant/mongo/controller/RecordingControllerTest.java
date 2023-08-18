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

    private final static String END_POINT_PATH = "/recordings";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private RecordingService recordingServ;
    private long idUser;
    private long idChat;
    private MockMultipartFile audioFile;
    private String mongoId;
    private RecordingResponse recordingResponse;

    @BeforeEach
    void setUp() {
        idUser = 1L;
        idChat = 1L;
        audioFile = new MockMultipartFile("test", "test.wav",
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new byte[100]);
        mongoId = "64debc2d04d1fc7bfe27a989";
        recordingResponse = RecordingResponse.builder()
                .idRecording("")
                .idChat(idChat)
                .idUser(idUser)
                .build();
    }

    @Test
    void givenRecordingsEndpoint_whenGetRequest_thenResponseStatusIsOk() throws Exception {
        mockMvc.perform(get("/recordings"))
                .andExpect(status().isOk());
    }

    @Test
    void givenAudioEndpoint_whenGetRequestWithUserIdAndChatId_thenResponseStatusIsOk()
            throws Exception {

        mockMvc.perform(get(END_POINT_PATH + "/audio/")
                        .param("idUser",  String.valueOf(idUser))
                        .param("idChat",  String.valueOf(idChat)))
                .andExpect(status().isOk());
    }

    @Test
    void givenAudioEndpoint_whenGetRequestWithNoUserAndNoIdAndChatId_thenResponseStatusIsBadRequest()
            throws Exception {
        mockMvc.perform(get(END_POINT_PATH + "/audio/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAudioEndpoint_whenGetRequestWithUserIdAndChatId_thenResponseStatusIsBad() throws Exception {
        mockMvc.perform(get(END_POINT_PATH + "/audio/")
                        .param("idUser", String.valueOf(idUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenARecordingRequest_whenMockRecordingController_thenVerifyResponseIsOk()
            throws Exception {
        mockMvc.perform(multipart("/recordings/")
                        .file(audioFile)
                        .param("idUser", Long.toString(idUser))
                        .param("idChat", Long.toString(idChat))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void givenAnId_whenMockRecordingController_thenVerifyResponseIsOk() throws Exception {
        when(recordingServ.getRecording(mongoId)).thenReturn(recordingResponse);
        mockMvc.perform(get("/recordings/audio/" + mongoId))
                .andExpect(status().isOk());
    }
}