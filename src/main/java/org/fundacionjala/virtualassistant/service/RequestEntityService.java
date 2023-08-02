package org.fundacionjala.virtualassistant.service;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;


public interface RequestEntityService {
  ResponseEntity<RequestEntity> createTextRequest(@RequestBody RequestEntity requestEntity, UriComponentsBuilder ucb);
}
