package com.qualiai.backend.dtos.auth;

import java.util.Date;

public record   UsuarioTokenDTO(
        Integer idUsuario,
        String nome,
        String email,
        String permissao,
        Boolean ativo,
        Boolean conta_bloqueada,
        Date conta_expira_em,
        Boolean senha_expirada
) {}
