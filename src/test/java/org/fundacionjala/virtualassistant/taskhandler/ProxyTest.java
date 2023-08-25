package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProxyTest {
    private Proxy proxy;

    @BeforeEach
    void setUp() {
        proxy = new Proxy();
    }

    @Test
    void shouldMatchWithContinueActionEnum() throws IntentException {
        String handledIntent = proxy.handleIntent("CONTINUE");
        System.out.println(handledIntent);
        assertNotNull(handledIntent);
    }

    @Test
    void shouldThrowAnIntentException() {
        IntentException exception = assertThrows(IntentException.class, () -> proxy.handleIntent(""));
        assertEquals(exception.getMessage(), IntentException.INTENT_NOT_FOUND);
    }
}