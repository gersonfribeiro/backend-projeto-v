CREATE TABLE errors
(
    id_error               SERIAL PRIMARY KEY,
    mensagem_erro          TEXT    NOT NULL,
    id_usuario_responsavel INTEGER NOT NULL,
    classname_trace        VARCHAR(255),
    methodname_trace       VARCHAR(255),
    filename_trace         VARCHAR(255),
    linenumber_trace       VARCHAR(255),
    hora_erro              DATE,
    status_code            INTEGER NOT NULL
);
