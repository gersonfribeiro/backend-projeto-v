package com.qualiai.backend.crud.domain.usuarios;

import com.qualiai.backend.utils.HeaderPaginator;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository {

    Optional<UsuarioDetails> selectUsuarioById(int idUsuario);

    Optional<UsuarioDetails> selectUsuarioByEmail(String emailUsuario);

    HeaderPaginator<Usuarios> selectUsuariosPaginator(int limite, int offset, String orderBy);

    boolean insertUsuario(Usuarios usuario);

    int validarEmail(String emailUsuario);

    boolean updateUsuario(Usuarios usuario);

    boolean deleteUsuario(int idUsuario);
}
