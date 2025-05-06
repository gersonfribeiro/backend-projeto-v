package com.qualiai.backend.crud.adapter.allExeptions;

import com.qualiai.backend.crud.adapter.allExeptions.crud.ErrorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static com.qualiai.backend.utils.data.StaticDateFormat.dataAtual;

@RestControllerAdvice
public class ErrorHandler {
    private final ErrorsService errorsService;

    public ErrorHandler(ErrorsService errorsService) {
        this.errorsService = errorsService;
    }

    // Exception genérica para interceptar todas as exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        StackTraceElement stackElement = e.getStackTrace()[0]; // primeiro elemento da stack
        Map<String, Object> traceInfo = Map.of(
                "className", stackElement.getClassName(),
                "methodName", stackElement.getMethodName(),
                "fileName", stackElement.getFileName(),
                "lineNumber", stackElement.getLineNumber()
        );

        if (e instanceof UsuarioErrorInterface usuarioErrorInterface) {
            errorResponse.setErro(usuarioErrorInterface.getErro());
            errorResponse.setUsuario(usuarioErrorInterface.getUsuario());
            errorResponse.setTrace(traceInfo);
            errorResponse.setHoraErro(usuarioErrorInterface.getHoraErro());
            errorResponse.setStatusCode(usuarioErrorInterface.getStatusCode());

            // Código para persistir o erro no banco de dados:
            errorsService.insertError(errorResponse);

            return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(usuarioErrorInterface.getStatusCode()));
        } else {
            // Tratamento genérico caso não seja tratável
            errorResponse.setErro("Erro interno não tratado");
            errorResponse.setUsuario(null);
            errorResponse.setTrace(traceInfo);
            errorResponse.setHoraErro(dataAtual());
            errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            // Código para persistir o erro no banco de dados:
            errorsService.insertError(errorResponse);

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
