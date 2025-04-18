package com.qualiai.backend.crud.adapter.auth;

import com.qualiai.backend.dtos.auth.AuthLoginDTO;
import com.qualiai.backend.dtos.auth.LoginResponse;
import com.qualiai.backend.dtos.auth.UpdateSenhaDTO;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthHandler authHandler;

    public AuthController(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthLoginDTO loginDTO) {
        return authHandler.login(loginDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioTokenDTO> extrairDados(@RequestParam String token) {
        return authHandler.extrairDados(token);
    }

    @PutMapping("/alter")
    public ResponseEntity<String> alter(
            @RequestBody UpdateSenhaDTO data,
            @RequestHeader("Authorization") String authorization
    ) {
        return authHandler.updateSenha(data, authorization);
    }

    @PutMapping("/controle")
    public ResponseEntity<String> controlarBloqueioContaUsuario(
            @RequestParam String emailUsuario,
            @RequestParam boolean operacao,
            @RequestParam boolean ativo,
            @RequestHeader("Authorization") String authorization
    ) {
        return authHandler.controlarBloqueioContaUsuario(emailUsuario, operacao, ativo, authorization);
    }

}
