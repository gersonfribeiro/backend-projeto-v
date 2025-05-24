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

    public Usuarios CreateUsuarioDefault() {
        Usuarios novoUsuario = new Usuarios();
        novoUsuario.setNome("ADMIN");
        novoUsuario.setEmail("admin@gmail.com");
        novoUsuario.setSenha("AdminQualiAI");
        novoUsuario.setPermissao("ADMINISTRADOR");
        novoUsuario.setAtivo(true);
        novoUsuario.setContaBloqueada(false);
        novoUsuario.setContaExpiraEm(null);
        novoUsuario.setSenhaExpirada(false);
        novoUsuario.setTentativasFalhas(0);
        return novoUsuario;
    }
}
