package com.qualiai.backend.crud.adapter.usuarios.exceptions;

import com.qualiai.backend.crud.adapter.allExeptions.UsuarioErrorInterface;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.qualiai.backend.utils.data.StaticDateFormat.dataAtual;

@Getter
public class EmailDuplicado extends RuntimeException implements UsuarioErrorInterface {

    private final UsuarioTokenDTO usuario;

    public EmailDuplicado(String message, UsuarioTokenDTO usuario) {
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
        return HttpStatus.CONFLICT.value();
    }
}
