package com.qualiai.backend.crud.adapter.auth;

import com.qualiai.backend.dtos.auth.AuthLoginDTO;
import com.qualiai.backend.dtos.auth.LoginResponse;
import com.qualiai.backend.dtos.auth.UpdateSenhaDTO;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler {
    private final AuthService authService;

    public AuthHandler(AuthService authService) {
        this.authService = authService;
    }

    public ResponseEntity<LoginResponse> login(AuthLoginDTO loginDTO) {
        LoginResponse login = authService.login(loginDTO);
        return new ResponseEntity<>(login, HttpStatus.CREATED);
    }

    public ResponseEntity<UsuarioTokenDTO> extrairDados(String token) {
        return ResponseEntity.ok(authService.extrairDados(token));
    }

    public ResponseEntity<String> updateSenha(UpdateSenhaDTO data, String authorization) {
        authService.updateSenha(data, authorization);
        String confirmacao = "Senha atualizada com sucesso!";
        return ResponseEntity.ok(confirmacao);
    }

    public ResponseEntity<String> controlarBloqueioContaUsuario(String emailUsuario, boolean operacao, boolean ativo, String authorization) {
        authService.controlarBloqueioContaUsuario(emailUsuario, operacao, ativo, authorization);
        String operacaoRealizada = String.format(
                "A conta para o e-mail: %s foi %s com sucesso!",
                emailUsuario, !operacao ? "desbloqueada" : "bloqueada");
        return ResponseEntity.ok(operacaoRealizada);
    }
}
