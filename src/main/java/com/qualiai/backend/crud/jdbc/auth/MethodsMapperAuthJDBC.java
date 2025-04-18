package com.qualiai.backend.crud.jdbc.auth;

import com.qualiai.backend.dtos.auth.UpdateSenhaDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class MethodsMapperAuthJDBC {

    protected MapSqlParameterSource params(UpdateSenhaDTO data) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email_usuario", data.getEmailUsuario())
                .addValue("nova_senha", data.getNovaSenha());
        return params;
    }

}
