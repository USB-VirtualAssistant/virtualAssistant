package org.fundacionjala.virtualassistant.conversation.controller;

import org.fundacionjala.virtualassistant.conversation.model.ConversationEntity;
import org.fundacionjala.virtualassistant.conversation.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {
    private final ConversationService service;

    @Autowired
    public ConversationController(ConversationService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<ConversationEntity> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getAll(page, size);
    }
}
