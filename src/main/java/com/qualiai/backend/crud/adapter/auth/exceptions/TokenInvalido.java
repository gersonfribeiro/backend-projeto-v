package com.qualiai.backend.crud.adapter.auth.exceptions;

import com.qualiai.backend.crud.adapter.allExeptions.UsuarioErrorInterface;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import org.springframework.http.HttpStatus;

import static com.qualiai.backend.utils.data.StaticDateFormat.dataAtual;

public class TokenInvalido extends RuntimeException implements UsuarioErrorInterface {

    public TokenInvalido(String message) {
        super(message);
    }

    @Override
    public String getErro() {
        return super.getMessage();
    }

    @Override
    public UsuarioTokenDTO getUsuario() {
        return null;
    }

    @Override
    public String getHoraErro() {
        return dataAtual();
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

}
