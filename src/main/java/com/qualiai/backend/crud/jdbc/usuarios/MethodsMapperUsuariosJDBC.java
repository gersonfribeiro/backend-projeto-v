package com.qualiai.backend.crud.jdbc.usuarios;

import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.ArrayList;
import java.util.List;

public class MethodsMapperUsuariosJDBC {
    protected RowMapper<UsuarioDetails> usuariosRowMapper() {
        return (rs, rowNum) -> {
            Usuarios usuario = new Usuarios();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome_usuario"));
            usuario.setEmail(rs.getString("email_usuario"));
            usuario.setSenha(rs.getString("senha_usuario"));
            usuario.setPermissao(rs.getString("permissao_usuario"));
            usuario.setAtivo(rs.getBoolean("ativo"));
            usuario.setContaBloqueada(rs.getBoolean("conta_bloqueada"));
            usuario.setContaExpiraEm(rs.getTimestamp("conta_expira_em"));
            usuario.setSenhaExpirada(rs.getBoolean("senha_expirada"));
            usuario.setTentativasFalhas(rs.getInt("tentativas_falhas"));

            return new UsuarioDetails(usuario);
        };
    }

    protected ResultSetExtractor<List<Usuarios>> usuariosResultSetExtractor() {
        return rs -> {
            List<Usuarios> usuarios = new ArrayList<>();

            while (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nome_usuario"));
                usuario.setEmail(rs.getString("email_usuario"));
                usuario.setSenha(rs.getString("senha_usuario"));
                usuario.setPermissao(rs.getString("permissao_usuario"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setContaBloqueada(rs.getBoolean("conta_bloqueada"));
                usuario.setContaExpiraEm(rs.getTimestamp("conta_expira_em"));
                usuario.setSenhaExpirada(rs.getBoolean("senha_expirada"));
                usuario.setTentativasFalhas(rs.getInt("tentativas_falhas"));

                usuarios.add(usuario);
            }
            return usuarios;
        };
    }

    protected MapSqlParameterSource parameterSource(Usuarios usuario) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_usuario", usuario.getIdUsuario());
        params.addValue("nome_usuario", usuario.getNome());
        params.addValue("email_usuario", usuario.getEmail());
        params.addValue("senha_usuario", usuario.getSenha());
        params.addValue("permissao_usuario", usuario.getPermissao());
        params.addValue("ativo", usuario.getAtivo());
        params.addValue("conta_bloqueada", usuario.getContaBloqueada());
        params.addValue("conta_expira_em", usuario.getContaExpiraEm());
        params.addValue("senha_expirada", usuario.getSenhaExpirada());
        params.addValue("tentativas_falhas", usuario.getTentativasFalhas());

        return params;
    }
}

