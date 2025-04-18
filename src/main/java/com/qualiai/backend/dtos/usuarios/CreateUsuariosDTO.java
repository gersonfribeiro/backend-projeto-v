package com.qualiai.backend.dtos.usuarios;

import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import lombok.Getter;

@Getter
public class CreateUsuariosDTO extends UsuariosBaseDTO{

    public Usuarios CreateUsuario() {
        Usuarios novoUsuario = new Usuarios();
        novoUsuario.setNome(this.nome);
        novoUsuario.setEmail(this.email);
        novoUsuario.setSenha(this.senha);
        novoUsuario.setPermissao(this.permissao);
        novoUsuario.setAtivo(this.ativo);
        novoUsuario.setContaBloqueada(this.contaBloqueada);
        novoUsuario.setContaExpiraEm(this.contaExpiraEm);
        novoUsuario.setSenhaExpirada(this.senhaExpirada);
        novoUsuario.setTentativasFalhas(this.tentativasFalhas);

        return novoUsuario;
    }
}
