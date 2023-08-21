package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class Connection {
    public static void main(String[] args) {
        Cluster cluster;
        Session session;

        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect();
        session.execute("CREATE KEYSPACE IF NOT EXISTS cassandra_keyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };");
        session.execute("CREATE TABLE IF NOT EXISTS cassandra_keyspace.audio_files (audio_id uuid PRIMARY KEY,audioFile blob);");
    }
}
