package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Row;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@SpringBootApplication
@Component
public class Operations {

    Connection connection = new Connection();
    Session session =connection.getSession();

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;

    @Value("${spring.data.cassandra.table-name}")
    private String tableName;

  public Operations() throws IOException {
  }

    public byte[] getAudioFile(UUID audioId) {
        PreparedStatement preparedStatement = session.prepare("SELECT audioFile FROM " + keySpace + "." + tableName + " WHERE audio_id = ?");
        BoundStatement boundStatement = preparedStatement.bind(audioId);
        ResultSet resultSet = session.execute(boundStatement);
        Row row = resultSet.one();
        if (row == null) {
            return null;
        }
        ByteBuffer buffer = row.getBytes("audioFile");
        return buffer.array();
    }


    public boolean uploadAudioFile(UUID audioId, byte[] audioData) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(audioData);
            PreparedStatement preparedStatement = session.prepare("INSERT INTO " + keySpace + "." + tableName + " (audio_id, audioFile) VALUES (?, ?)");
            BoundStatement boundStatement = preparedStatement.bind(audioId, buffer);
            session.execute(boundStatement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
