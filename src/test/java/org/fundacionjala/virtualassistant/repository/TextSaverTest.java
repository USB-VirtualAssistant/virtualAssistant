package org.fundacionjala.virtualassistant.repository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.*;
public class TextSaverTest {

    @Test
    public void testSaveText_SuccessfulUpdate() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        DriverManager mockDriverManager = mock(DriverManager.class);
        when(mockDriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

        TextSaverImpl textSaver = new TextSaverImpl();

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = textSaver.saveText("Updated text", 123);

        verify(mockPreparedStatement).setString(1, "Updated text");
        verify(mockPreparedStatement).setInt(2, 123);

        assertTrue(result);
    }
}
