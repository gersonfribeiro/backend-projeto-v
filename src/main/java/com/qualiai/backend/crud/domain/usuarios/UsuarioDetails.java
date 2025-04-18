package com.qualiai.backend.crud.domain.usuarios;

import com.qualiai.backend.utils.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.qualiai.backend.utils.data.DataAtual.dataSemFormatacao;

public class UsuarioDetails implements UserDetails {
    private final Usuarios usuario;

    public UsuarioDetails(Usuarios usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        try {
            return Permissao.valueOf(this.usuario.getPermissao()).getAuthorities();
        } catch (IllegalArgumentException | NullPointerException e) {
            return List.of();
        }
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.usuario.getSenha();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.usuario.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !usuario.getContaBloqueada();
    }

    @Override
    public boolean isAccountNonExpired() {
        return usuario.getContaExpiraEm() == null || usuario.getContaExpiraEm().after(dataSemFormatacao());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.getSenhaExpirada() == null || !usuario.getSenhaExpirada();
    }

    @Override
    public boolean isEnabled() {
        return usuario.getAtivo();
    }

    public Usuarios getUsuario() {
        return this.usuario;
    }
}
