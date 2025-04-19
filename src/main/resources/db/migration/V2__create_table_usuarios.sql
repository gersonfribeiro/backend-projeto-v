CREATE TABLE usuarios
(
    id_usuario        SERIAL PRIMARY KEY,
    nome_usuario      VARCHAR(255) NOT NULL,
    email_usuario     VARCHAR(255) NOT NULL UNIQUE,
    senha_usuario     VARCHAR(255) NOT NULL,
    permissao_usuario VARCHAR(255) NOT NULL,
    ativo             BOOLEAN DEFAULT TRUE,
    conta_bloqueada   BOOLEAN DEFAULT FALSE,
    conta_expira_em   DATE,
    senha_expirada    BOOLEAN,
    tentativas_falhas INT     DEFAULT 0
);
