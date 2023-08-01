CREATE TABLE IF NOT EXISTS db_schema.response (
    id_response SERIAL PRIMARY KEY,
    text VARCHAR(150),
    date TIMESTAMP
);