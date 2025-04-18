package com.qualiai.backend.crud.jdbc.usuarios;

import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import com.qualiai.backend.crud.domain.usuarios.UsuariosRepository;
import com.qualiai.backend.utils.HeaderPaginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.qualiai.backend.crud.jdbc.usuarios.UsuariosSQL.*;

@Repository
public class UsuariosJDBC extends MethodsMapperUsuariosJDBC implements UsuariosRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuariosJDBC.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UsuariosJDBC(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<UsuarioDetails> selectUsuarioById(int idUsuario) {
        List<UsuarioDetails> usuarios;
        try {
            MapSqlParameterSource params = new MapSqlParameterSource("id_usuario", idUsuario);
            usuarios = jdbcTemplate.query(sqlSelectUsuarioById(), params, usuariosRowMapper());
            return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<UsuarioDetails> selectUsuarioByEmail(String emailUsuario) {
        List<UsuarioDetails> usuarios;
        try {
            MapSqlParameterSource params = new MapSqlParameterSource("email_usuario", emailUsuario);
            usuarios = jdbcTemplate.query(sqlSelectUsuarioByEmail(), params, usuariosRowMapper());
            return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public HeaderPaginator<Usuarios> selectUsuariosPaginator(int limite, int offset, String orderBy) {
        try {
            int offsetCalculado = Math.max((offset - 1) * limite, 0);
            limite = Math.min(Math.max(limite, 1), 100);

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("limite", limite)
            .addValue("offset", offsetCalculado);

            int totalRegistros = Math.max(jdbcTemplate.queryForObject(sqlCountAllUsuarios(), new MapSqlParameterSource(), Integer.class), 0);
            int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);
            List<Usuarios> registros = jdbcTemplate.query(sqlUsuariosPaginator(orderBy), params, usuariosResultSetExtractor());

            return new HeaderPaginator<>(
                    limite,
                    offset,
                    totalPaginas,
                    totalRegistros,
                    registros
            );

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean insertUsuario(Usuarios usuario) {
        try {
            return jdbcTemplate.update(sqlInsertUsuario(), parameterSource(usuario)) > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public int validarEmail(String emailUsuario) {
        try {
            return jdbcTemplate.queryForObject(sqlValidarEmail(), new MapSqlParameterSource("email_usuario", emailUsuario), Integer.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean updateUsuario(Usuarios usuario) {
        try {
            return jdbcTemplate.update(sqlUpdateUsuario(), parameterSource(usuario)) > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deleteUsuario(int id_usuario) {
        try {
            return jdbcTemplate.update(sqlDeleteUsuario(), new MapSqlParameterSource("id_usuario", id_usuario)) > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
