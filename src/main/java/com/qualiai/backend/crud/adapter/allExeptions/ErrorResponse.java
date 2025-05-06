package com.qualiai.backend.crud.adapter.allExeptions;

import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

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

    public MapSqlParameterSource paramsException() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mensagem_erro", erro);
        params.addValue("id_usuario_responsavel", usuario.idUsuario());
        params.addValue("classname_trace", trace.get("className"));
        params.addValue("methodname_trace", trace.get("methodName"));
        params.addValue("filename_trace", trace.get("fileName"));
        params.addValue("linenumber_trace", trace.get("lineNumber"));
        params.addValue("hora_erro", horaErro);
        params.addValue("status_code", statusCode);
        return params;
    }
}
