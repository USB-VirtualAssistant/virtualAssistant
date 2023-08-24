package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.Properties;

@Data
@Component
public class CassandraConnection implements DisposableBean{

    private Cluster cluster;
    private Session session;
    private Resource resource;
    private Properties props;
    private String keyspace;
    private String table;
    private String contactPoint;
    private static final String PROPERTIES_FILE_PATH = "/application.properties";
    private CassandraQueries cassandraQueries;

    public CassandraConnection() throws IOException {
        resource = new ClassPathResource(PROPERTIES_FILE_PATH);
        props = PropertiesLoaderUtils.loadProperties(resource);
        keyspace = props.getProperty("spring.data.cassandra.keyspace-name");
        table = props.getProperty("spring.data.cassandra.table-name");
        contactPoint = props.getProperty("spring.data.cassandra.contact-points");
        this.cluster = Cluster.builder().addContactPoint(contactPoint).build();
        this.session = cluster.connect();
        this.cassandraQueries = new CassandraQueries(session,keyspace,table);
    }

    public void createSchema() {
        cassandraQueries.createKeySpace(keyspace);
        cassandraQueries.createTable();
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
