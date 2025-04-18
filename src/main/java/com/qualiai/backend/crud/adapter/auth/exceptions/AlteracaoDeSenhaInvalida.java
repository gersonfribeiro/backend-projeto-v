package com.qualiai.backend.crud.adapter.auth.exceptions;

import com.qualiai.backend.crud.adapter.allExeptions.UsuarioErrorInterface;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import org.springframework.http.HttpStatus;

import static com.qualiai.backend.utils.data.StaticDateFormat.dataAtual;

public class AlteracaoDeSenhaInvalida extends RuntimeException implements UsuarioErrorInterface {

    private final UsuarioTokenDTO usuario;

    public AlteracaoDeSenhaInvalida(String message, UsuarioTokenDTO usuario) {
        super(message);
        this.usuario = usuario;
    }

    @Override
    public String getErro() {
        return super.getMessage();
    }

    @Override
    public UsuarioTokenDTO getUsuario() { return this.usuario; }

    @Override
    public String getHoraErro() {
        return dataAtual();
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
