package com.qualiai.backend.crud.adapter.usuarios;

import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import com.qualiai.backend.dtos.usuarios.CreateUsuariosDTO;
import com.qualiai.backend.dtos.usuarios.UpdateUsuariosDTO;
import com.qualiai.backend.utils.HeaderPaginator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosHandler usuariosHandler;

    public UsuariosController(UsuariosHandler usuariosHandler) {
        this.usuariosHandler = usuariosHandler;
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Optional<UsuarioDetails>> selectUsuarioById(
            @PathVariable int idUsuario,
            @RequestHeader("Authorization") String authorization
    ) {
        return usuariosHandler.selectUsuarioById(idUsuario, authorization);
    }

    @GetMapping()
    public ResponseEntity<Optional<UsuarioDetails>> selectUsuarioByEmail(
            @RequestParam String emailUsuario,
            @RequestHeader("Authorization") String authorization
    ) {
        return usuariosHandler.selectUsuarioByEmail(emailUsuario, authorization);
    }

    @GetMapping("/consulta")
    public ResponseEntity<HeaderPaginator<Usuarios>> selectUsuariosPaginator(
            @RequestParam(defaultValue = "25") int limite,
            @RequestParam(defaultValue = "1") int offset,
            @RequestParam(defaultValue = "DESC") String orderBy,
            @RequestHeader("Authorization") String authorization
    ) {
        return usuariosHandler.selectUsuariosPaginator(limite, offset, orderBy, authorization);
    }

    @PostMapping()
    public ResponseEntity<Usuarios> insertUsuario(
            @RequestBody CreateUsuariosDTO usuario,
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        return usuariosHandler.insertUsuario(usuario, authorization);
    }

    @PutMapping()
    public ResponseEntity<Usuarios> updateUsuario(
            @RequestBody UpdateUsuariosDTO usuario,
            @RequestParam int idUsuario,
            @RequestHeader("Authorization") String authorization
    ) {
        return usuariosHandler.updateUsuario(usuario, idUsuario, authorization);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> deleteUsuario(
            @PathVariable int idUsuario,
            @RequestHeader("Authorization") String authorization
    ) {
        return usuariosHandler.deleteUsuario(idUsuario, authorization);
    }
}
