package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.DisposableBean;

@Data
@Component
@Import(CassandraConfig.class)
public class CassandraConnection implements DisposableBean, TempDataBase {

    private Cluster cluster;
    private Session session;
    private String keyspace;
    private String table;
    private String contactPoint;
    private static final String PROPERTIES_FILE_PATH = "/application.properties";
    private CassandraQueries cassandraQueries;

    @Autowired
    public CassandraConnection(CassandraConfig config) {
        keyspace = config.getKeyspace();
        table = config.getTable();
        contactPoint = config.getContactPoint();
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
