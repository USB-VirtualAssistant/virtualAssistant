package org.fundacionjala.virtualassistant.cassandra;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CassandraConfig {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.data.cassandra.table-name}")
    private String table;

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoint;

}
