package org.fundacionjala.virtualassistant.textrequest.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.service.TextRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    @GetMapping("/{userId}")
    public List<RequestEntity> getTextRequests(@NotNull @PathVariable Long userId,
                                               @NotNull @RequestParam(name = "id_context") Long contextID) {
        return requestEntityService.getTextRequestByUserAndContext(userId, contextID);
    }
}
