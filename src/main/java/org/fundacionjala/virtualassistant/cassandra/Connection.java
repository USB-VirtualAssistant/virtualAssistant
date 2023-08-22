package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class Connection {

    private Cluster cluster;
    @Getter
    private Session session;

    Resource resource = new ClassPathResource("/application.properties");
    Properties props = PropertiesLoaderUtils.loadProperties(resource);

    private String keyspace = props.getProperty("spring.data.cassandra.keyspace-name");
    private String table = props.getProperty("spring.data.cassandra.table-name");

    public Connection() throws IOException {
        this.cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        this.session = cluster.connect();
    }

    public void createSchema() {
        createKeyspace();
        createTable();
    }

    public void createKeyspace() {
        String query ="CREATE KEYSPACE IF NOT EXISTS " +keyspace+ " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };";
        session.execute(query);
    }

    public void createTable() {
        String query ="CREATE TABLE IF NOT EXISTS "+ keyspace+"."+table +" (audio_id uuid PRIMARY KEY,audioFile blob);";
        session.execute(query);
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
