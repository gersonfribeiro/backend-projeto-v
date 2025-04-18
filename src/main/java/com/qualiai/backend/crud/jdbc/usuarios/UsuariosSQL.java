package com.qualiai.backend.crud.jdbc.usuarios;

public class UsuariosSQL {

    public static String baseQuerySelectUsuarios(String sqlComplementar) {
        return """ 
                \s
                SELECT
                    usuarios.id_usuario,
                    usuarios.nome_usuario,
                    usuarios.email_usuario,
                    usuarios.senha_usuario,
                    usuarios.permissao_usuario,
                    usuarios.ativo,
                    usuarios.conta_bloqueada,
                    usuarios.conta_expira_em,
                    usuarios.senha_expirada,
                    usuarios.tentativas_falhas
                FROM usuarios
                """ + sqlComplementar + """
                """;
    }

    public static String sqlSelectUsuarioById() {
        String sqlComplementar = """
                WHERE usuarios.id_usuario = :id_usuario;
                """;
        return baseQuerySelectUsuarios(sqlComplementar);
    }

    public static String sqlSelectUsuarioByEmail() {
        String sqlComplementar = """
                WHERE usuarios.email_usuario = :email_usuario;
                """;
        return baseQuerySelectUsuarios(sqlComplementar);
    }

    public static String sqlUsuariosPaginator(String orderBy) {
        if (!orderBy.matches("(?i)ASC|DESC"))
            orderBy = "DESC";

        String sqlComplementar = """
                ORDER BY usuarios.id_usuario""" + ' ' + orderBy + ' ' + """
                LIMIT :limite OFFSET :offset;
                """;
        return baseQuerySelectUsuarios(sqlComplementar);
    }

    public static String sqlCountAllUsuarios() {
        return """
                \s
                SELECT COUNT(*) as total_usuarios FROM usuarios;
                \s
                """;
    }

    public static String sqlInsertUsuario() {
        return """
                \s
                INSERT INTO usuarios (
                    nome_usuario,
                    email_usuario,
                    senha_usuario,
                    permissao_usuario,
                    ativo,
                    conta_bloqueada,
                    conta_expira_em,
                    senha_expirada,
                    tentativas_falhas
                )
                VALUES (
                    :nome_usuario,
                    :email_usuario,
                    :senha_usuario,
                    :permissao_usuario,
                    :ativo,
                    :conta_bloqueada,
                    :conta_expira_em,
                    :senha_expirada,
                    :tentativas_falhas
                );
                \s
                """;
    }

    public static String sqlValidarEmail() {
        return """
                \s
                SELECT COUNT(*) AS email_encontrado FROM usuarios WHERE email_usuario = :email_usuario;
                \s
                """;
    }

    public static String sqlUpdateUsuario() {
        return """
                \s
                UPDATE usuarios
                SET
                    nome_usuario = :nome_usuario,
                    email_usuario = :email_usuario,
                    senha_usuario = :senha_usuario,
                    permissao_usuario = :permissao_usuario,
                    ativo = :ativo,
                    conta_bloqueada = :conta_bloqueada,
                    conta_expira_em = :conta_expira_em,
                    senha_expirada = :senha_expirada,
                    tentativas_falhas = :tentativas_falhas
                WHERE usuarios.id_usuario = :id_usuario;
                \s
                """;
    }

    public static String sqlDeleteUsuario() {
        return """
                \s
                DELETE FROM usuarios
                WHERE usuarios.id_usuario = :id_usuario;
                \s
                """;
    }
}
