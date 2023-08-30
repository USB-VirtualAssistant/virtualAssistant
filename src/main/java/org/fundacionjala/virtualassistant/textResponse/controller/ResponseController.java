package org.fundacionjala.virtualassistant.textResponse.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.springframework.http.ResponseEntity;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

@RestController
@RequestMapping("/textResponse")
public class ResponseController {
    TextResponseService responseService;

    public ResponseController (TextResponseService textResponseService) {
        this.responseService = textResponseService;
    }

    @PostMapping
    public ResponseEntity<TextResponse> createTextResponse(@Valid @RequestBody ParameterResponse response) {
        TextResponse textResponse = responseService.save(response.getIdRequest(), response.getText());
        return new ResponseEntity<>(textResponse, CREATED);
    }
}
