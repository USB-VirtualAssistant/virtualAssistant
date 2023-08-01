CREATE TABLE IF NOT EXISTS db_schema.request_answer (
    id_request_answer SERIAL PRIMARY KEY,
    id_chat INT,
    id_request INT,
    id_response INT,
    FOREIGN KEY (id_chat) REFERENCES db_schema.chat (id_chat),
    FOREIGN KEY (id_request) REFERENCES db_schema.request (id_request),
    FOREIGN KEY (id_response) REFERENCES db_schema.response (id_response)
);