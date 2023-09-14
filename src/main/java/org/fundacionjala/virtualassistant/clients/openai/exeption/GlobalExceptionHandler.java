package org.fundacionjala.virtualassistant.clients.openai.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.theokanning.openai.OpenAiHttpException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OpenAiHttpException.class)
    public ResponseEntity<String> handleOpenAiHttpException(OpenAiHttpException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

}
