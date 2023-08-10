package org.fundacionjala.virtualassistant.textrequest.controller;

import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.service.TextRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/textRequest")
public class RequestEntityController {
    @Autowired
    TextRequestService requestEntityService;

    @PostMapping
    public ResponseEntity<TextRequestResponse> createTextRequest(@RequestBody TextRequest textRequest) throws TextRequestException {
        TextRequestResponse textRequestResponse = requestEntityService.createTextRequest(textRequest);
        return new ResponseEntity<>(textRequestResponse, CREATED);
    }
}
