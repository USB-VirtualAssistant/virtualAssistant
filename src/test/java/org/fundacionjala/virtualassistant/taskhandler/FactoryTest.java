package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.actions.GetAlbumsAction;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.SpotifyTaskActionFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class FactoryTest {

    private MusicService musicService;
    private TaskActionFactory<SpotifyIntent> factory;

    @BeforeEach
    void setUp() {
        musicService = mock(MusicService.class);
        factory = new SpotifyTaskActionFactory(musicService);
    }

    @Test
    void givenFactoryWhenCreatingNewFactoryThenCreated() {
        assertNotNull(factory);
    }

    @Test
    void givenEnumWhenCreatingNewFactoryThenExpectedEnum() throws IntentException {
        TaskAction action = factory.createTaskAction(SpotifyIntent.GET_ALBUMS);

        assertNotNull(action);
        assertEquals(GetAlbumsAction.class, action.getClass());
    }
}
