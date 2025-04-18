package com.qualiai.backend.crud.jdbc.auth;

import com.qualiai.backend.crud.domain.auth.AuthRepository;
import com.qualiai.backend.dtos.auth.UpdateSenhaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.qualiai.backend.crud.jdbc.auth.AuthSQL.*;

@Repository
public class AuthJDBC extends MethodsMapperAuthJDBC implements AuthRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthJDBC.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthJDBC(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateSenha(UpdateSenhaDTO data) {
        try {
            jdbcTemplate.update(sqlUpdateSenha(), params(data));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void tentativaLoginIncorreto(String emailUsuario, int tentativas) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("email_usuario", emailUsuario)
            .addValue("tentativas_falhas", tentativas);
            jdbcTemplate.update(sqlAtualizarTentativasIncorretas(), params);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void controlarBloqueioContaUsuario(String emailUsuario, boolean operacao, boolean ativo) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("email_usuario", emailUsuario)
            .addValue("conta_bloqueada", operacao)
            .addValue("ativo", ativo);
            jdbcTemplate.update(sqlBloquearConta(), params);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

}
