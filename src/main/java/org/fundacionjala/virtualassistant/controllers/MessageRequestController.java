package org.fundacionjala.virtualassistant.controllers;

import org.fundacionjala.virtualassistant.models.TextMessageRequestModel;
import org.fundacionjala.virtualassistant.models.TextMessageResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/message")
public class MessageRequestController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/aiRequest")
    public String chat(@RequestParam("prompt") String prompt) {
        TextMessageResponseModel chatGptResponse = getCurrentProcessedResponse(prompt);
        return extractActMessageContentData(chatGptResponse);
    }

    private TextMessageResponseModel getCurrentProcessedResponse(String prompt) {
        TextMessageRequestModel request = createActualRequest(prompt);
        return template.postForObject(apiURL, request, TextMessageResponseModel.class);
    }

    private TextMessageRequestModel createActualRequest(String prompt) {
        return new TextMessageRequestModel(model, prompt);
    }

    private String extractActMessageContentData(TextMessageResponseModel currentResponse) {
        return currentResponse.getChoices().get(0).getMessage().getContent();
    }
}
