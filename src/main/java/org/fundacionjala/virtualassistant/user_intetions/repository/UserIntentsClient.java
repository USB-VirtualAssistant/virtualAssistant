package org.fundacionjala.virtualassistant.user_intetions.repository;

import org.springframework.http.ResponseEntity;

public interface UserIntentsClient {
    ResponseEntity<String> processUserIntentsByMicroService(String input);
}
