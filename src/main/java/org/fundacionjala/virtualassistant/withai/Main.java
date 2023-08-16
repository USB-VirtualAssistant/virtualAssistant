package org.fundacionjala.virtualassistant.withai;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Define the user input
        String userInput = "What is the weather like today?";

        // Make a POST request to the With AI API
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.wit.ai/language?v=20230215&q=bonjour");

        // Set the request headers
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer YOUR_API_KEY");

        // Set the request body
        StringEntity requestBody = new StringEntity("{\"text\": \"" + userInput + "\"}", "UTF-8");
        httpPost.setEntity(requestBody);

        try {
            // Send the POST request
            HttpResponse response = httpClient.execute(httpPost);

            // Get the response body
            String responseBody = EntityUtils.toString(response.getEntity());

            // Print the response
            System.out.println(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

