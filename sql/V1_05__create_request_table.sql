CREATE TABLE IF NOT EXISTS db_schema.request (
    id_request SERIAL PRIMARY KEY,
    text VARCHAR(100),
    date TIMESTAMP,
    id_audio_mongo VARCHAR
);