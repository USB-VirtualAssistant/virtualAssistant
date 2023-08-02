package org.fundacionjala.virtualassistant.service;

import java.net.URI;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class RequestEntityServiceImp implements RequestEntityService{
  @Autowired
  RequestEntityRepository requestEntityRepository;

  @Override
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
