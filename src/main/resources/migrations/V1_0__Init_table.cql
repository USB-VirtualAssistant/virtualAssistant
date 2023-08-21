CREATE KEYSPACE IF NOT EXISTS cassandra_keyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

CREATE TABLE IF NOT EXISTS cassandra_keyspace.audio_files (
                                                         audio_id uuid PRIMARY KEY,
                                                         audioFile blob
);
