CREATE TABLE IF NOT EXISTS db_schema.response (
    id_response SERIAL PRIMARY KEY,
    id_request INTEGER UNIQUE REFERENCES db_schema.request (id_request),
    text VARCHAR(500),
    date TIMESTAMP
);