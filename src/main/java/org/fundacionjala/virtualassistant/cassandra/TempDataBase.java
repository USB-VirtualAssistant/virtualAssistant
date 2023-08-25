package org.fundacionjala.virtualassistant.cassandra;

public interface TempDataBase {
    void createSchema();
    void close();
}
