package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@Component
public class CassandraOperations {

    private final Session session;
    private final CassandraQueries cassandraQueries;
    private static final String AUDIO_FILE = "audioFile";

    public CassandraOperations() throws IOException {
        CassandraConnection cassandraConnection = new CassandraConnection();
        session = cassandraConnection.getSession();
        cassandraQueries = new CassandraQueries(session, cassandraConnection.getKeyspace(), cassandraConnection.getTable());
    }

    public byte[] getAudioFile(UUID audioId) {
        try {
            BoundStatement boundStatement = cassandraQueries.getAudioFileQuery().bind(audioId);
            ResultSet resultSet = session.execute(boundStatement);
            Row row = resultSet.one();
            ByteBuffer buffer = row.getBytes(AUDIO_FILE);
            return buffer.array();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public boolean uploadAudioFile(UUID audioId, byte[] audioData) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(audioData);
            BoundStatement boundStatement = cassandraQueries.uploadAudioFileQuery().bind(audioId, buffer);
            session.execute(boundStatement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
