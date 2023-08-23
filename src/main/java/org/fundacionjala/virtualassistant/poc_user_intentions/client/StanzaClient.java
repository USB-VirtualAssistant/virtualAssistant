package org.fundacionjala.virtualassistant.poc_user_intentions.client;

import org.fundacionjala.virtualassistant.poc_user_intentions.repository.UserIntentsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class StanzaClient implements UserIntentsClient {

    private RestTemplate restTemplate;
    private String stanzaMsUrl;

    @Autowired
    public StanzaClient(@Value("${stanza.ms.url}") String stanzaMsUrl) {
        restTemplate = new RestTemplate();
        this.stanzaMsUrl = stanzaMsUrl;
    }

    @GetMapping("/handleStanzaMicroService")
    @Override
    public ResponseEntity<String> processUserIntentsByMicroService(@RequestParam("input") String input) {
        String urlWithInput = stanzaMsUrl + "?input_text=" + input;
        return restTemplate.getForEntity(urlWithInput, String.class);
    }
}

