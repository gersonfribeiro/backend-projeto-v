package com.qualiai.backend.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthLoginDTO {

    @JsonProperty("email_usuario")
    private String emailUsuario;

    @JsonProperty("senha_usuario")
    private String senhaUsuario;
}
