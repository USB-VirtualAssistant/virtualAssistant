package org.fundacionjala.virtualassistant.controller;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.service.TextRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/textRequest")
public class TextRequestController {

    private TextRequestService textRequestServiceParam;

    @GetMapping("/{userId}?id_context={id_context}")
    public List<RequestEntity> getTextRequests(@PathVariable Long userID, @RequestParam Long contextID) {
        return textRequestServiceParam.getTextRequestByUserAndContext(userID, contextID);
    }

}
