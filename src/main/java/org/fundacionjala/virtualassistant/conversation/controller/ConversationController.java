package org.fundacionjala.virtualassistant.conversation.controller;

import org.fundacionjala.virtualassistant.conversation.model.ConversationResponse;
import org.fundacionjala.virtualassistant.conversation.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/")
public class ConversationController {
    private final ConversationService service;

    @Autowired
    public ConversationController(ConversationService service) {
        this.service = service;
    }

    @GetMapping("users/{userId}/contexts/{contextId}")
    public ResponseEntity<List<ConversationResponse>> getAll(
            @PathVariable Long userId,
            @PathVariable Long contextId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ConversationResponse> conversations = service.getAllByUserAndContext(userId, contextId, page, size);
        return ResponseEntity.ok(conversations);
    }
}
