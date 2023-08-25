package org.fundacionjala.virtualassistant.poc_user_intentions.client;
import org.fundacionjala.virtualassistant.poc_user_intentions.repository.UserIntentsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Component 
public class StanzaClient implements UserIntentsClient {

    private RestTemplate restTemplate;
    private String stanzaMsUrl;
    private static final String INPUT_TEXT_PARAM = "?input_text=";

    @Autowired
    public StanzaClient(@Value("${stanza.ms.url}") String stanzaMsUrl) {
        restTemplate = new RestTemplate();
        this.stanzaMsUrl = stanzaMsUrl;
    }

    @Override
    public ResponseEntity<String> processUserIntentsByMicroService(String input) {
        StringBuilder urlBuilder = new StringBuilder(stanzaMsUrl).append(INPUT_TEXT_PARAM);
        urlBuilder.append(input);
        return restTemplate.getForEntity(urlBuilder.toString(), String.class);
    }
}
