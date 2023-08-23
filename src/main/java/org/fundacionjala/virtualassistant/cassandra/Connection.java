package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.Properties;

@Component
public class Connection implements DisposableBean{

    private Cluster cluster;
    @Getter
    private Session session;
    private Resource resource;
    private Properties props;
    private String keyspace = props.getProperty("spring.data.cassandra.keyspace-name");
    private String table = props.getProperty("spring.data.cassandra.table-name");
    private String contactPoint = props.getProperty("spring.data.cassandra.contact-points");
    private static final String PROPERTIES_FILE_PATH = "/application.properties";

    public Connection() throws IOException {
        this.cluster = Cluster.builder().addContactPoint(contactPoint).build();
        this.session = cluster.connect();
        resource = new ClassPathResource(PROPERTIES_FILE_PATH);
        props = PropertiesLoaderUtils.loadProperties(resource);
    }

    public void createSchema() {
        createKeyspace();
        createTable();
    }

    public void createKeyspace() {
        String query ="CREATE KEYSPACE IF NOT EXISTS " + keyspace + " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };";
        session.execute(query);
    }

    public void createTable() {
        String query ="CREATE TABLE IF NOT EXISTS "+ keyspace + "." + table + " (audio_id uuid PRIMARY KEY,audioFile blob);";
        session.execute(query);
    }

    public void close() {
        session.close();
        cluster.close();
    }
    @Override
    public void destroy() {
        close();
    }
}
