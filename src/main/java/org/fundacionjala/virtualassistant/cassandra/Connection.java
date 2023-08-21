package org.fundacionjala.virtualassistant.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class Connection {
    public static void main(String[] args) {
        Cluster cluster;
        Session session;

        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("cassandra_keyspace");

        session.execute("INSERT INTO cassandra_keyspace.shopping_cart (userid, item_count, last_update_timestamp)VALUES ('9876', 2, toTimeStamp(now()));");
    }
}
