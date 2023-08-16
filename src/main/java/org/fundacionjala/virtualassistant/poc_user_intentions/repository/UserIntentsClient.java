package org.fundacionjala.virtualassistant.poc_user_intentions.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserIntentsClient {
    ResponseEntity<String> processUserIntentsByMicroService(@RequestParam("input") String input);
}
