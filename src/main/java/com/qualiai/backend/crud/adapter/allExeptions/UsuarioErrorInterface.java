package com.qualiai.backend.crud.adapter.allExeptions;

import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;

public interface UsuarioErrorInterface {
    String getErro();
    UsuarioTokenDTO getUsuario();
    int getStatusCode();
    String getHoraErro();
}
