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
public class WITClient implements UserIntentsClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String witMsUrl;

    @Autowired
    public WITClient(@Value("${wit.ms.url}") String witMsUrl) {
        this.witMsUrl = witMsUrl;
    }

    @Override
    @GetMapping("/handleWitMicroService")
    public ResponseEntity<String> processUserIntentsByMicroService(@RequestParam("input") String input) {
        return restTemplate.getForEntity(witMsUrl + "?input_data=" + input, String.class);
    }
}
