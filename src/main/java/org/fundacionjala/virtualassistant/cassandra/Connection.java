package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Data
@Component
public class Connection {
    private Cluster cluster;
    @Getter
    private Session session;
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;
    @Value("${spring.data.cassandra.table-name}")
    private String tableName;

    public Connection() {
        this.cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        this.session = cluster.connect();
    }
    public void createSchema() {
        createKeyspace();
        createTable();
    }
    public void createKeyspace() {
        String query ="CREATE KEYSPACE IF NOT EXISTS " +keySpace+ " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };";
        session.execute(query);
    }

    public void createTable() {
        String query ="CREATE TABLE IF NOT EXISTS "+ keySpace+"."+tableName +" (audio_id uuid PRIMARY KEY,audioFile blob);";
        session.execute(query);
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
