package org.fundacionjala.virtualassistant.redis.service;

import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.exception.RedisDataNotFoundException;
import org.fundacionjala.virtualassistant.redis.repository.AudioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AudioServiceTest {

    @InjectMocks
    private AudioService audioService;

    @Mock
    private AudioRepository audioRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private static final int DELETION_TIME = 3600;
    private static final String FILE_NAME = "file";
    private static final String ORIGINAL_FILE_NAME = "Question.wav";
    private static final String CONTENT_TYPE = "audio/wav";
    private static final String MOCK_ID = "test-id";
    private static final String MOCK_NON_EXISTENT_ID = "non-existent-id";
    private MockMultipartFile mockAudio;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        File audioFile = new ClassPathResource(ORIGINAL_FILE_NAME).getFile();
        mockAudio = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, Files.readAllBytes(audioFile.toPath()));
    }

    @Test
    void testSave() throws FileSaveException, IOException {
        Audio audio = new Audio();
        audio.setId(MOCK_ID);
        audio.setAudioFile(mockAudio.getBytes());

        when(audioRepository.save(any(Audio.class))).thenReturn(audio);

        Audio savedAudio = audioService.save(mockAudio);

        verify(audioRepository).save(any(Audio.class));
        verify(valueOperations).set(MOCK_ID, mockAudio.getBytes(), DELETION_TIME, TimeUnit.SECONDS);

        assertEquals(audio.getId(), savedAudio.getId());
    }

    @Test
    void testFindById() throws RedisDataNotFoundException, IOException {
        String base64EncodedContent = Base64.getEncoder().encodeToString(mockAudio.getBytes());

        when(valueOperations.get(MOCK_ID)).thenReturn(base64EncodedContent);

        byte[] audioData = audioService.findById(MOCK_ID);

        verify(valueOperations).get(MOCK_ID);

        assertArrayEquals(mockAudio.getBytes(), audioData);
    }

    @Test
    void testSaveRedisDataNotFoundException() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenThrow(new IOException("Simulated IOException"));

        verify(audioRepository, never()).save(any(Audio.class));
        verify(redisTemplate, never()).opsForValue();

        assertThrows(FileSaveException.class, () -> { audioService.save(mockFile); });
    }

    @Test
    void testFindByIdRedisDataNotFoundException() {
        when(valueOperations.get(MOCK_NON_EXISTENT_ID)).thenReturn(null);
        assertThrows(RedisDataNotFoundException.class, () -> audioService.findById(MOCK_NON_EXISTENT_ID));
    }
}