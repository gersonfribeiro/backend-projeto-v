package com.qualiai.backend.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import com.qualiai.backend.dtos.auth.LoginResponse;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final String secret = "655ed07d-e6d7-4fad-b0e5-baf97df840f6-777fb814-d6ca-4056-9c09-f1cc4e8b967a";

    public LoginResponse generateToken(UsuarioDetails usuarioDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Usuarios usuario = usuarioDetails.getUsuario();
            String tokenJWT = JWT.create()
                    .withIssuer("qualiais")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .withClaim("idUsuario", usuario.getIdUsuario())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("permissao", usuario.getPermissao())
                    .withClaim("ativo", usuario.getAtivo())
                    .withClaim("conta_bloqueada", usuario.getContaBloqueada())
                    .withClaim("conta_expira_em", usuario.getContaExpiraEm())
                    .withClaim("senha_expirada", usuario.getSenhaExpirada())
                    .sign(algorithm);
            return new LoginResponse(tokenJWT);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token", e);
        }
//        } catch (JWTCreationException e) {
//            throw new TokenInvalido("Erro ao gerar o token");
//        }
    }

    public String validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("qualiais")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token", e);
        }
//        } catch (JWTVerificationException e) {
//            throw new TokenInvalido("Token inválido ou expirado");
//        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
    }

    public UsuarioTokenDTO extrairDados(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("qualiais")
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            return new UsuarioTokenDTO(
                    jwt.getClaim("idUsuario").asInt(),
                    jwt.getClaim("nome").asString(),
                    jwt.getSubject(),
                    jwt.getClaim("permissao").asString(),
                    jwt.getClaim("ativo").asBoolean(),
                    jwt.getClaim("conta_bloqueada").asBoolean(),
                    jwt.getClaim("conta_expira_em").asDate(),
                    jwt.getClaim("senha_expirada").asBoolean()
            );
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token", e);
        }
//        } catch (JWTVerificationException e) {
//            throw new TokenInvalido("Token inválido ou expirado");
//        }
    }
}
