package com.qualiai.backend.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSenhaDTO {
    private String emailUsuario;
    private String senhaUsuario;
    private String novaSenha;
    private String confirmarNovaSenha;
}
