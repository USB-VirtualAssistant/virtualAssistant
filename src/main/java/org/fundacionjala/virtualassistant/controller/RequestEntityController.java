package org.fundacionjala.virtualassistant.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/textRequest/")
public class RequestEntityController {
  @Autowired
  RequestEntityRepository requestEntityRepository;

  @PostMapping
  public ResponseEntity<RequestEntity> createTextRequest(@RequestBody RequestEntity requestEntity) {
    return new ResponseEntity<>(requestEntity, HttpStatus.CREATED);
  }
}
