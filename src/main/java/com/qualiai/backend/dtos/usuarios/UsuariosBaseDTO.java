package com.qualiai.backend.dtos.usuarios;

import lombok.Getter;

import java.util.Date;

@Getter
public class UsuariosBaseDTO {
    protected String nome;
    protected String email;
    protected String senha;
    protected String permissao;
    protected boolean ativo;
    protected boolean contaBloqueada;
    protected Date contaExpiraEm;
    protected boolean senhaExpirada;
    protected int tentativasFalhas;
}
