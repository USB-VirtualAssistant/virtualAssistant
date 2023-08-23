package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Row;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@SpringBootApplication
@Component
public class Operations {

    Connection connection;
    Session session;
    private String keyspace;
    private String table;

    public Operations() throws IOException {
        connection = new Connection();
        session = connection.getSession();
        keyspace = connection.getKeyspace();
        table = connection.getTable();
    }

    public byte[] getAudioFile(UUID audioId) {
        PreparedStatement preparedStatement = session.prepare("SELECT audioFile FROM " + keyspace + "." + table + " WHERE audio_id = ?");
        BoundStatement boundStatement = preparedStatement.bind(audioId);
        ResultSet resultSet = session.execute(boundStatement);
        Row row = resultSet.one();
        if (row == null) {
            return new byte[0];
        }
        ByteBuffer buffer = row.getBytes("audioFile");
        return buffer.array();
    }

    public boolean uploadAudioFile(UUID audioId, byte[] audioData) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(audioData);
            PreparedStatement preparedStatement = session.prepare("INSERT INTO " + keyspace + "." + table + " (audio_id, audioFile) VALUES (?, ?)");
            BoundStatement boundStatement = preparedStatement.bind(audioId, buffer);
            session.execute(boundStatement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
