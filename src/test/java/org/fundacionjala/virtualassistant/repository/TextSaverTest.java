package org.fundacionjala.virtualassistant.repository;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.*;
public class TextSaverTest {
    @Test
    public void testSaveText_SuccessfulUpdate(){
        RequestEntityRepository mockRepository = mock(RequestEntityRepository.class);
        TextSaverImpl textSaver = new TextSaverImpl(mockRepository);

        String text = "Test Text";
        int idAudioMongo = 123;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setText(text);
        requestEntity.setIdAudioMongo(idAudioMongo);

        when(mockRepository.save(any(RequestEntity.class))).thenReturn(requestEntity);

        RequestEntity result = textSaver.saveText(text, idAudioMongo);

        assertNotNull(result);
        assertSame(result.getText(), requestEntity.getText());
        assertSame(result.getIdAudioMongo(), requestEntity.getIdAudioMongo());
    }
}
