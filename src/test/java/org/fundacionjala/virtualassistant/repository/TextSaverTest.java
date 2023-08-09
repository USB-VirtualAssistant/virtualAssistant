package org.fundacionjala.virtualassistant.repository;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.junit.jupiter.api.Test;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextSaverTest {
    @Test
    public void testSaveText_SuccessfulUpdate(){
        RequestEntityRepository mockRepository = mock(RequestEntityRepository.class);
        TextSaverImpl textSaver = new TextSaverImpl(mockRepository);

        String text = "Test Text";
        Timestamp data = new Timestamp(System.currentTimeMillis());
        int idAudioMongo = 123;

        RequestEntity requestEntity = new RequestEntity(text,data,idAudioMongo);
        requestEntity.setText(text);
        requestEntity.setIdAudioMongo(idAudioMongo);

        when(mockRepository.save(any(RequestEntity.class))).thenReturn(requestEntity);

        RequestEntity result = textSaver.saveText(text, idAudioMongo);

        assertNotNull(result);
        assertSame(result.getText(), requestEntity.getText());
        assertSame(result.getIdAudioMongo(), requestEntity.getIdAudioMongo());
    }
}
