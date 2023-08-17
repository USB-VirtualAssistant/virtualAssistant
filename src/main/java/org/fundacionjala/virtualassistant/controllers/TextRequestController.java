package org.fundacionjala.virtualassistant.controllers;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.service.TextRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/textRequest")
public class TextRequestController {
    private TextRequestService textRequestServiceParam;

    @GetMapping("/{userId}")
    public List<RequestEntity> getTextRequests(@PathVariable Long userId, @RequestParam(name = "id_context") Long contextID) {
        return textRequestServiceParam.getTextRequestByUserAndContext(userId, contextID);
    }
}