CREATE TABLE IF NOT EXISTS db_schema.context (
    id_context SERIAL PRIMARY KEY,
    title VARCHAR(45),
    id_request INT,
    FOREIGN KEY (id_request) REFERENCES db_schema.request (id_request)
);