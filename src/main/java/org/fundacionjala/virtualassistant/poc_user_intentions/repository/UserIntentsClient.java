package org.fundacionjala.virtualassistant.poc_user_intentions.repository;

import org.springframework.http.ResponseEntity;

public interface UserIntentsClient {
    ResponseEntity<String> processUserIntentsByMicroService(String input);
}

