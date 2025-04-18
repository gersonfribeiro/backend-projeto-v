package com.qualiai.backend.crud.jdbc.auth;

public class AuthSQL {

    public static String sqlUpdateSenha() {
        return """
                \s
                UPDATE usuarios
                SET senha_usuario = :nova_senha
                WHERE usuarios.email_usuario = :email_usuario;
                """;
    }

    public static String sqlAtualizarTentativasIncorretas() {
        return """
                \s
                UPDATE usuarios
                SET tentativas_falhas = :tentativas_falhas
                WHERE usuarios.email_usuario = :email_usuario;
                """;
    }

    public static String sqlBloquearConta() {
        return """
                \s
                UPDATE usuarios
                SET
                    conta_bloqueada = :conta_bloqueada,
                    ativo = :ativo
                WHERE usuarios.email_usuario = :email_usuario;
                """;
    }
}
