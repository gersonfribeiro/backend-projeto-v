package com.qualiai.backend.auth;


import com.qualiai.backend.crud.adapter.usuarios.exceptions.UsuarioNaoEncontrado;
import com.qualiai.backend.crud.domain.usuarios.UsuariosRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {


    private final UsuariosRepository usuariosRepository;

    public AuthorizationService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailUsuario) throws UsuarioNaoEncontrado {
        return usuariosRepository.selectUsuarioByEmail(emailUsuario).
                orElseThrow(
                        () -> new UsuarioNaoEncontrado(
                                String.format(
                                        "Usuário não encontrado para o email: %s", emailUsuario
                                ), null
                        )
                );
    }
}
