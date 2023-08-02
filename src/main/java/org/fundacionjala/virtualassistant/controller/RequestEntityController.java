package org.fundacionjala.virtualassistant.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.service.RequestEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/textRequest")
public class RequestEntityController {
  @Autowired
  RequestEntityService requestEntityService;

  @PostMapping
  public ResponseEntity<RequestEntity> createTextRequest(@RequestBody RequestEntity requestEntity, UriComponentsBuilder ucb) {
    return requestEntityService.createTextRequest(requestEntity, ucb);
  }
}
