CREATE TABLE IF NOT EXISTS db_schema.request (
    id_request SERIAL PRIMARY KEY,
    id_user INT,
    text VARCHAR(150),
    date TIMESTAMP,
    id_audio_mongo VARCHAR,
    FOREIGN KEY (id_user) REFERENCES db_schema.user (id_user)
);