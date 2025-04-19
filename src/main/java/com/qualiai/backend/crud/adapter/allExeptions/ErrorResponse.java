package com.qualiai.backend.crud.adapter.allExeptions;

import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String erro;
    private UsuarioTokenDTO usuario;
    private Map<String, Object> trace;
    private String horaErro;
    private int statusCode;
}
