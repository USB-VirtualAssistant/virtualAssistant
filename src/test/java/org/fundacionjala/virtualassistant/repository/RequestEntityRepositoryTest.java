package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestEntityRepositoryTest {
  private RequestEntityRepository repository;

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
    RequestEntity request = new RequestEntity("hello", new Date(), 2L);
    when(repository.save(any(RequestEntity.class))).thenReturn(request);

    assertEquals(repository.save(request), request);
  }
}