package com.qualiai.backend.crud.domain.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuarios {
    private int idUsuario;
    private String nome;
    private String email;

    @JsonIgnore
    private String senha;
    private String permissao;
    private Boolean ativo;
    private Boolean contaBloqueada;
    private Date contaExpiraEm;
    private Boolean senhaExpirada;
    private int tentativasFalhas;
}
