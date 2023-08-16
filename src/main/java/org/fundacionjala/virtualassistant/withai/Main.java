package org.fundacionjala.virtualassistant.withai;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        String userInput = "How is the weather today?";

        String encodedInput = URLEncoder.encode(userInput, StandardCharsets.UTF_8);
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.wit.ai/message?v=20230215&q=" + encodedInput);

        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", "Bearer PYMNGHHB77HJZOX3L2VYXE6I4TXVAYTA");

        try {
            HttpResponse response = httpClient.execute(httpGet);

            String responseBody = EntityUtils.toString(response.getEntity());

            System.out.println(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

