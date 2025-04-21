package com.qualiai.backend.crud.adapter.usuarios;

import com.qualiai.backend.crud.adapter.auth.exceptions.PermissaoNegada;
import com.qualiai.backend.crud.adapter.usuarios.exceptions.EmailDuplicado;
import com.qualiai.backend.crud.adapter.usuarios.exceptions.UsuarioNaoEncontrado;
import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import com.qualiai.backend.crud.domain.usuarios.UsuariosRepository;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import com.qualiai.backend.dtos.usuarios.CreateUsuariosDTO;
import com.qualiai.backend.dtos.usuarios.UpdateUsuariosDTO;
import com.qualiai.backend.utils.HeaderPaginator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.qualiai.backend.utils.VerificarAutorizacoes.autorizacaoUsuarios;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuariosService(
            UsuariosRepository usuariosRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UsuarioDetails> selectUsuarioById(int idUsuario, String authorization) {
        Optional<UsuarioDetails> usuario = usuariosRepository.selectUsuarioById(idUsuario);
        UsuarioTokenDTO usuarioToken = autorizacaoUsuarios(authorization, "GET");
        if (usuario.isEmpty()) {
            String mensagemErro = String.format("Usuário não encontrado para o registro %d", idUsuario);
            throw new UsuarioNaoEncontrado(mensagemErro, usuarioToken);
        }
        return usuario;
    }

    public Optional<UsuarioDetails> selectUsuarioByEmail(String emailUsuario, String authorization) {
        Optional<UsuarioDetails> usuario = usuariosRepository.selectUsuarioByEmail(emailUsuario);
        UsuarioTokenDTO usuarioToken = null;

        if (authorization != null)
            usuarioToken = autorizacaoUsuarios(authorization, "GET");

        if (usuario.isEmpty()) {
            String mensagemErro = String.format("Usuário não encontrado para o email: %s", emailUsuario);
            throw new UsuarioNaoEncontrado(mensagemErro, usuarioToken);
        }
        return usuario;
    }

    public HeaderPaginator<Usuarios> selectUsuariosPaginator(
            int limite,
            int offset,
            String orderBy,
            String authorization
    ) {
        autorizacaoUsuarios(authorization, "GET");
        if (limite > 100) {
            limite = 100;
        }
        return usuariosRepository.selectUsuariosPaginator(limite, offset, orderBy);
    }

    public Usuarios insertUsuario(CreateUsuariosDTO usuario, String authorization) {
        if (authorization != null)
            autorizacaoUsuarios(authorization, "POST");
        Usuarios novoUsuario = usuario.CreateUsuario();
        validarEmail(novoUsuario.getEmail(), authorization);

        novoUsuario.setAtivo(false);
        novoUsuario.setContaBloqueada(true);
        novoUsuario.setTentativasFalhas(0);
        novoUsuario.setSenha(this.passwordEncoder.encode(novoUsuario.getSenha()));

        usuariosRepository.insertUsuario(novoUsuario);
        return novoUsuario;
    }

    public void validarEmail(String emailUsuario, String authorization) {
        UsuarioTokenDTO usuarioToken = null;
        if (authorization != null) {
            usuarioToken = autorizacaoUsuarios(authorization, "GET");
        }
        int validarEmail = usuariosRepository.validarEmail(emailUsuario);
        if (validarEmail > 0) {
            String mensagemErro = String.format("O email informado já está cadastrado: %s", emailUsuario);
            throw new EmailDuplicado(mensagemErro, usuarioToken);
        }
    }

    public Usuarios updateUsuario(UpdateUsuariosDTO usuario, int idUsuario, String authorization) {
        UsuarioTokenDTO usuarioToken = autorizacaoUsuarios(authorization, "PUT");

        if (!usuarioToken.permissao().equals("ADMINISTRADOR") && !usuarioToken.idUsuario().equals(idUsuario))
            throw new PermissaoNegada("Você não tem permissão para modificar essa conta", usuarioToken);

        Optional<UsuarioDetails> usuarioDomain = selectUsuarioById(idUsuario, authorization);

        Usuarios usuarioAtualizado = usuario.UpdateUsuario(idUsuario);
        if (!Objects.equals(usuarioAtualizado.getEmail(), usuarioDomain.get().getUsername()))
            validarEmail(usuarioAtualizado.getEmail(), authorization);
        usuarioAtualizado.setSenha(this.passwordEncoder.encode((usuarioAtualizado.getSenha())));
        usuariosRepository.updateUsuario(usuarioAtualizado);
        return usuarioAtualizado;
    }

    public void deleteUsuario(int idUsuario, String authorization) {
        autorizacaoUsuarios(authorization, "DELETE");
        selectUsuarioById(idUsuario, authorization);
        usuariosRepository.deleteUsuario(idUsuario);
    }
}
