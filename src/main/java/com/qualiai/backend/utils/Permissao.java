package com.qualiai.backend.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Permissao {
    ADMINISTRADOR(List.of("ROLE_ADMINISTRADOR", "ROLE_GERENCIA", "ROLE_RELATORIO", "ROLE_USUARIO")),
    USUARIOS(List.of("ROLE_USUARIO"));

    private final List<String> roles;

    Permissao(List<String> roles) {
        this.roles = roles;
    }

    public List<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }
}
