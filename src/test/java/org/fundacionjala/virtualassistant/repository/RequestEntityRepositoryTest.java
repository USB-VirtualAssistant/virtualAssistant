package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestEntityRepositoryTest {
  private RequestEntityRepository repository;

  private static final String REQUEST_TEXT = "hello";
  private static final ZonedDateTime REQUEST_DATE = ZonedDateTime.of(2023, 8, 2, 12, 0, 0, 0, ZoneId.systemDefault());
  private static final String ID_AUDIO_A = "mongo_id";
  private static final Long CONTEXT_ID_1 = 1L;
  private static final Long USER_ID_2 = 2L;

  @BeforeEach
  void setUp() {
    repository = mock(RequestEntityRepository.class);
  }

  @Test
  void repositoryLoads() {
    assertNotNull(repository);
  }

  @Test
  void addUserWithMock() {
    RequestEntity request = new RequestEntity();
    when(repository.save(any(RequestEntity.class))).thenReturn(request);

    assertEquals(repository.save(request), request);
  }
}