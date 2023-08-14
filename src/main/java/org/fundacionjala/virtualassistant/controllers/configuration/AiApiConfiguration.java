package org.fundacionjala.virtualassistant.controllers.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class AiApiConfiguration {

    private final static String API_KEY_HEADER = "Authorization";
    private final static String API_KEY_PREFIX = "Bearer ";

    @Value("${openai.apiKey}")
    private String openaiApiKey;

    @Bean
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(getAuthenticationInterceptor()));
        return restTemplate;
    }

    private ClientHttpRequestInterceptor getAuthenticationInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().add(API_KEY_HEADER, API_KEY_PREFIX + openaiApiKey);
            return execution.execute(request, body);
        };
    }

    public String getOpenaiApiKey() {
        return openaiApiKey;
    }
}
