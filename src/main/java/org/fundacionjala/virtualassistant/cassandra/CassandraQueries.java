package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class CassandraQueries {

    private final Session session;
    private final String keyspace;
    private final String table;

    public CassandraQueries(Session session, String keyspace, String table) {
        this.session = session;
        this.keyspace = keyspace;
        this.table = table;
    }

    public PreparedStatement getAudioFileQuery() {
        return session.prepare("SELECT audioFile FROM " + keyspace + "." + table + " WHERE audio_id = ?");
    }

    public PreparedStatement uploadAudioFileQuery() {
        return session.prepare("INSERT INTO " + keyspace + "." + table + " (audio_id, audioFile) VALUES (?, ?)");
    }

    public void createKeySpace(String keyspace){
        session.execute("CREATE KEYSPACE IF NOT EXISTS " + keyspace + " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };");
    }

    public void createTable(){
        session.execute("CREATE TABLE IF NOT EXISTS " + keyspace + "." + table + " (audio_id uuid PRIMARY KEY,audioFile blob);");
    }
}
