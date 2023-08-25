package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingletonServicesTest {
    private MusicService first;
    private MusicService second;

    @BeforeEach
    void setUp() {
        first = SingletonServices.getMusicService();
        second = SingletonServices.getMusicService();
    }

    @Test
    void shouldBeTheSameReferenceFor2DifferentInstances() {
        assertEquals(first, second);
    }
}