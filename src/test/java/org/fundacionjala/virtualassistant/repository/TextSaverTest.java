package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        LocalDate localDate = LocalDate.now();
        java.util.Date date= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        int idAudioMongo = 123;

        RequestEntity requestEntity = new RequestEntity(text,date,idAudioMongo);

        when(mockRepository.save(any(RequestEntity.class))).thenReturn(requestEntity);

        RequestEntity result = textSaver.saveText(text, idAudioMongo);

        assertNotNull(result);
        assertSame(result.getText(), requestEntity.getText());
        assertSame(result.getIdAudioMongo(), requestEntity.getIdAudioMongo());
    }
}
