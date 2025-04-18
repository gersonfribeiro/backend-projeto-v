package com.qualiai.backend.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Permissao {
    ADMINISTRADOR_AUTORIZADO(List.of("ROLE_ADMINISTRADOR", "ROLE_AUTORIZA_SAIDAS", "ROLE_GERENCIA", "ROLE_RELATORIO")),
    ADMINISTRADOR(List.of("ROLE_ADMINISTRADOR", "ROLE_GERENCIA", "ROLE_RELATORIO")),
    EMITE_AUTORIZACAO(List.of("ROLE_AUTORIZA_SAIDAS", "ROLE_GERENCIA", "ROLE_RELATORIO")),
    EMITE_SAIDA(List.of("ROLE_EMITE_SAIDA")),
    PORTARIA(List.of("ROLE_PORTARIA"));

    private final List<String> roles;

    Permissao(List<String> roles) {
        this.roles = roles;
    }

    public List<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

}
