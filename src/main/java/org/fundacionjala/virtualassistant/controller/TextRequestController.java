package org.fundacionjala.virtualassistant.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.service.TextRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/textRequest")
public class TextRequestController {

    private TextRequestService textRequestService;

    @GetMapping("/{userId}?contextId={contextID}")
    public List<RequestEntity> getTextRequests(@PathVariable Long userID, @RequestParam Long contextID) {
        return textRequestService.getTextRequestByUserAndContext(userID, contextID);
    }

}
