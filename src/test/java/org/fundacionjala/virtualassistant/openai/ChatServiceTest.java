import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChatServiceTest {

    @Mock
    private OpenAiClient openAiClient;

    private ChatService chatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        chatService = new ChatService(openAiClient);
    }

    @Test
    public void testChatService() {
        String inputMessage = "Hola, ¿cómo estás?";
        String expectedResponse = "¡Hola! Estoy bien.";

        // Simulamos el comportamiento del cliente de OpenAI
        when(openAiClient.getToken()).thenReturn("fake-token");
        when(openAiClient.buildCompletionRequest(inputMessage)).thenReturn(/* ... */);

        // Simulamos la respuesta de OpenAI
        when(openAiClient.buildCompletionRequest(inputMessage).getChoices().get(0).getText())
                .thenReturn(expectedResponse);

        // Llamamos al método del servicio y verificamos la respuesta
        String actualResponse = chatService.chat(inputMessage);
        assertEquals(expectedResponse, actualResponse);

        // Verificamos si los métodos del cliente de OpenAI se llamaron correctamente
        verify(openAiClient).getToken();
        verify(openAiClient).buildCompletionRequest(inputMessage);
    }
}
