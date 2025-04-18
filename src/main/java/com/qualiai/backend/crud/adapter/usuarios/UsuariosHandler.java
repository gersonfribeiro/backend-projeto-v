package com.qualiai.backend.crud.adapter.usuarios;

import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import com.qualiai.backend.dtos.usuarios.CreateUsuariosDTO;
import com.qualiai.backend.dtos.usuarios.UpdateUsuariosDTO;
import com.qualiai.backend.utils.HeaderPaginator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuariosHandler {
    private final UsuariosService usuariosService;

    public UsuariosHandler(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    public ResponseEntity<Optional<UsuarioDetails>> selectUsuarioById(int idUsuario, String authorization) {
        Optional<UsuarioDetails> usuario = usuariosService.selectUsuarioById(idUsuario, authorization);
        return ResponseEntity.ok(usuario);
    }

    public ResponseEntity<Optional<UsuarioDetails>> selectUsuarioByEmail(String emailUsuario, String authorization) {
        Optional<UsuarioDetails> usuario = usuariosService.selectUsuarioByEmail(emailUsuario, authorization);
        return ResponseEntity.ok(usuario);
    }

    public ResponseEntity<HeaderPaginator<Usuarios>> selectUsuariosPaginator(
            int limite,
            int offset,
            String orderBy,
            String authorization
    ) {
        HeaderPaginator<Usuarios> usuario = usuariosService.selectUsuariosPaginator(
                limite,
                offset,
                orderBy,
                authorization
        );
        return ResponseEntity.ok(usuario);
    }

    public ResponseEntity<Usuarios> insertUsuario(CreateUsuariosDTO usuario, String authorization) {
        Usuarios novoUsuario = usuariosService.insertUsuario(usuario, authorization);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    public ResponseEntity<Usuarios> updateUsuario(UpdateUsuariosDTO usuario, int idUsuario, String authorization) {
        Usuarios usuarioAtualizado = usuariosService.updateUsuario(usuario, idUsuario, authorization);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    public ResponseEntity<String> deleteUsuario(int idUsuario, String authorization) {
        usuariosService.deleteUsuario(idUsuario, authorization);
        return ResponseEntity.noContent().build();
    }
}
