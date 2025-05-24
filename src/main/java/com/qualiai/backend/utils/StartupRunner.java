package com.qualiai.backend.utils;

import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import com.qualiai.backend.crud.domain.usuarios.UsuariosRepository;
import com.qualiai.backend.dtos.usuarios.CreateUsuariosDTO;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner {
    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    public StartupRunner(UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            if (usuariosRepository.selectUsuarioById(1).isEmpty()) {
                Usuarios novoUsuario = new CreateUsuariosDTO().CreateUsuarioDefault();
                novoUsuario.setSenha(this.passwordEncoder.encode(novoUsuario.getSenha()));
                novoUsuario.setIdUsuario(1);

                usuariosRepository.insertUsuario(novoUsuario);
            }
        };
    }
}
