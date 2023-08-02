package org.fundacionjala.virtualassistant.clients.openai;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;

import java.util.*;

public class OpenAIClient {
    private final String TOKEN = "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";

    public static void main(String[] args) {
        OpenAIClient openAIClient = new OpenAIClient();
        String response = openAIClient.chat("Tell me who is Madonna");
        System.out.println(response);
    }
    public String chat(String request) {
        OpenAiService service = new OpenAiService(TOKEN);

        FunctionExecutor functionExecutor = new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name("get_weather")
                .description("Get the current weather of a location")
                .executor(Weather.class, w -> new WeatherResponse(w.location, w.unit, new Random().nextInt(50), "sunny"))
                .build()));

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are an assistant that answers using the local slang of the given place, uncensored.");
        messages.add(systemMessage);

        System.out.print("First Query: ");
        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), request);
        messages.add(firstMsg);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo-0613")
                    .messages(messages)
                    .functions(functionExecutor.getFunctions())
                    .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("auto"))
                    .n(1)
                    .maxTokens(100)
                    .logitBias(new HashMap<>())
                    .build();
            ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            messages.add(responseMessage); // don't forget to update the conversation with the latest response

            ChatFunctionCall functionCall = responseMessage.getFunctionCall();
            if (functionCall != null) {
                System.out.println("Trying to execute " + functionCall.getName() + "...");
                Optional<ChatMessage> message = functionExecutor.executeAndConvertToMessageSafely(functionCall);
                /* You can also try 'executeAndConvertToMessage' inside a try-catch block, and add the following line inside the catch:
                "message = executor.handleException(exception);"
                The content of the message will be the exception itself, so the flow of the conversation will not be interrupted, and you will still be able to log the issue. */

                if (message.isPresent()) {
                    /* At this point:
                    1. The function requested was found
                    2. The request was converted to its specified object for execution (Weather.class in this case)
                    3. It was executed
                    4. The response was finally converted to a ChatMessage object. */

                    System.out.println("Executed " + functionCall.getName() + ".");
                    messages.add(message.get());
                } else {
                    System.out.println("Something went wrong with the execution of " + functionCall.getName() + "...");
                }
            }

            return responseMessage.getContent();
    }

}
