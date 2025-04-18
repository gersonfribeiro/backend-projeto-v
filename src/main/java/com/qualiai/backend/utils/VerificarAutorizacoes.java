package com.qualiai.backend.utils;

import com.qualiai.backend.auth.TokenService;
import com.qualiai.backend.crud.adapter.auth.exceptions.PermissaoNegada;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;

public class VerificarAutorizacoes {

    private static final TokenService tokenService = new TokenService();

    public static UsuarioTokenDTO getUsuarioByToken(String authorization) {
        String token = authorization.replace("Bearer ", "");
        return tokenService.extrairDados(token);
    }

    public static UsuarioTokenDTO autorizacaoUsuarios(String authorization, String metodo) {
        UsuarioTokenDTO usuario = getUsuarioByToken(authorization);
        String mensagem = switch (metodo) {
            case "POST" -> "Você não tem permissão para realizar o registro de novos usuários.";
            case "PUT" -> "Você não tem permissão para realizar a modificação deste usuário.";
            case "DELETE" -> "Você não tem permissão para realizar a exclusão deste usuário.";
            default -> "Você não tem permissão para realizar consulta de usuários.";
        };

        if (!usuario.permissao().equals("ADMINISTRADOR"))
            throw new PermissaoNegada(
                    mensagem,
                    usuario
            );
        return usuario;
    }

    public static UsuarioTokenDTO autorizacaoControleBloqueio(String authorization, boolean operacao) {
        UsuarioTokenDTO usuario = getUsuarioByToken(authorization);
        String mensagem = String.format("Você não tem permissão para realizar o %s deste usuário.", !operacao ? "desbloqueio" : "bloqueio");
        if (!usuario.permissao().equals("ADMINISTRADOR"))
            throw new PermissaoNegada(
                    mensagem,
                    usuario
            );
        return usuario;
    }
}
