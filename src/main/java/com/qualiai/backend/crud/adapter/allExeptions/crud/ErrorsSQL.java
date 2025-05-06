package com.qualiai.backend.crud.adapter.allExeptions.crud;

public class ErrorsSQL {
    public static String sqlInsertError() {
        return
                """
                \s
                INSERT INTO errors (
                    mensagem_erro,
                    id_usuario_responsavel,
                    classname_trace,
                    methodname_trace,
                    filename_trace,
                    linenumber_trace,
                    hora_erro,
                    status_code
                ) VALUES (
                    :mensagem_erro,
                    :id_usuario_responsavel,
                    :classname_trace,
                    :methodname_trace,
                    :filename_trace,
                    :linenumber_trace,
                    :hora_erro,
                    :status_code
                )
                """;
    }
}
