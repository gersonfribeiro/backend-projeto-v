package com.qualiai.backend.crud.adapter.allExeptions.crud;

import com.qualiai.backend.crud.adapter.allExeptions.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;

import static com.qualiai.backend.crud.adapter.allExeptions.crud.ErrorsSQL.*;

@Repository
public class ErrorsJDBC extends ErrorResponse implements ErrorsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorsJDBC.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ErrorsJDBC(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void inserir(Errors errors) {
        try {
            jdbcTemplate.update(sqlInsertError(), paramsException());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
