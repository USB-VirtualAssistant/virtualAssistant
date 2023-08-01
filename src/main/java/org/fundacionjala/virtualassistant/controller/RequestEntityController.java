package org.fundacionjala.virtualassistant.controller;

import java.net.URI;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/textRequest")
public class RequestEntityController {
  @Autowired
  RequestEntityRepository requestEntityRepository;

  @PostMapping
  public ResponseEntity<RequestEntity> createTextRequest(@RequestBody RequestEntity requestEntity, UriComponentsBuilder ucb) {
    if (requestEntity.getIdUser() == null || requestEntity.getIdUser() <= 0) {
      return ResponseEntity.internalServerError().build();
    }
    
    RequestEntity savedRequestEntity = requestEntityRepository.save(requestEntity);

    URI locationOfNewRequestEntity = ucb
        .path("textRequest/{id}")
        .buildAndExpand(savedRequestEntity.getIdRequest())
        .toUri();
    return ResponseEntity.created(locationOfNewRequestEntity).build();
  }
}
