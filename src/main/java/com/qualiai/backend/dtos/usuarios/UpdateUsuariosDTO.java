package com.qualiai.backend.dtos.usuarios;

import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import lombok.Getter;

@Getter
public class UpdateUsuariosDTO extends UsuariosBaseDTO {

    public Usuarios UpdateUsuario(int idUsuario) {
        Usuarios usuarioAtualizado = new Usuarios();
        usuarioAtualizado.setIdUsuario(idUsuario);
        usuarioAtualizado.setNome(this.nome);
        usuarioAtualizado.setEmail(this.email);
        usuarioAtualizado.setPermissao(this.permissao);
        usuarioAtualizado.setAtivo(this.ativo);
        usuarioAtualizado.setContaBloqueada(this.contaBloqueada);
        usuarioAtualizado.setContaExpiraEm(this.contaExpiraEm);
        usuarioAtualizado.setSenhaExpirada(this.senhaExpirada);
        usuarioAtualizado.setTentativasFalhas(this.tentativasFalhas);

        return usuarioAtualizado;
    }
}
