package com.qualiai.backend.crud.adapter.allExeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static com.qualiai.backend.utils.data.StaticDateFormat.dataAtual;

@RestControllerAdvice
public class ErrorHandler {

    //    Exception genérica para interceptar todas as exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception e) {
        StackTraceElement stackElement = e.getStackTrace()[0]; // primeiro elemento da stack
        Map<String, Object> traceInfo = Map.of(
                "className", stackElement.getClassName(),
                "methodName", stackElement.getMethodName(),
                "fileName", stackElement.getFileName(),
                "lineNumber", stackElement.getLineNumber()
        );

        if (e instanceof UsuarioErrorInterface usuarioErrorInterface) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErro(usuarioErrorInterface.getErro());
            errorResponse.setUsuario(usuarioErrorInterface.getUsuario());
            errorResponse.setTrace(traceInfo);
            errorResponse.setHoraErro(usuarioErrorInterface.getHoraErro());
            errorResponse.setStatusCode(usuarioErrorInterface.getStatusCode());

            return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(usuarioErrorInterface.getStatusCode()));
        }

        // Tratamento genérico caso não seja tratável
        ErrorResponse defaultError = new ErrorResponse(
                "Erro interno não tratado",
                null,
                0,
                traceInfo,
                dataAtual(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
