package org.fundacionjala.virtualassistant.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.service.TextRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/textRequest")
public class TextRequestController {

    TextRequestService textRequestService;

    @GetMapping("/{userId}?contextId={contextID}")
    public List<RequestEntity> getTextRequests(@PathVariable Long userID, @PathVariable Long contextID) {
        return textRequestService.getAllTextRequest(userID, contextID);
    }

}
