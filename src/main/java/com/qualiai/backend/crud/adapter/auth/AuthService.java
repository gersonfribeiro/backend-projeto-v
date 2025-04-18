package com.qualiai.backend.crud.adapter.auth;

import com.qualiai.backend.auth.TokenService;
import com.qualiai.backend.crud.adapter.auth.exceptions.AlteracaoDeSenhaInvalida;
import com.qualiai.backend.crud.adapter.auth.exceptions.ContaBloqueada;
import com.qualiai.backend.crud.adapter.auth.exceptions.LoginIncorreto;
import com.qualiai.backend.crud.adapter.auth.exceptions.MaximoTentativasIncorretas;
import com.qualiai.backend.crud.adapter.usuarios.UsuariosService;
import com.qualiai.backend.crud.adapter.usuarios.exceptions.UsuarioNaoEncontrado;
import com.qualiai.backend.crud.domain.auth.AuthRepository;
import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.Usuarios;
import com.qualiai.backend.dtos.auth.AuthLoginDTO;
import com.qualiai.backend.dtos.auth.LoginResponse;
import com.qualiai.backend.dtos.auth.UpdateSenhaDTO;
import com.qualiai.backend.dtos.auth.UsuarioTokenDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.qualiai.backend.utils.VerificarAutorizacoes.autorizacaoControleBloqueio;
import static com.qualiai.backend.utils.VerificarAutorizacoes.getUsuarioByToken;
import static com.qualiai.backend.utils.data.DataAtual.dataSemFormatacao;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UsuariosService usuariosService;

    public AuthService(
            AuthRepository authRepository,
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            PasswordEncoder passwordEncoder,
            UsuariosService usuariosService
    ) {
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.usuariosService = usuariosService;
    }

    public LoginResponse login(AuthLoginDTO loginDTO) {
        UsuarioDetails usuarioDetails = usuariosService
                .selectUsuarioByEmail(loginDTO.getEmailUsuario(), null)
                .orElseThrow(() -> new UsuarioNaoEncontrado("Usuário não encontrado com email: " + loginDTO.getEmailUsuario(), null));

        Usuarios usuario = usuarioDetails.getUsuario();

        if (usuario.getContaExpiraEm() != null && !usuario.getContaExpiraEm().before(dataSemFormatacao())) {
            // Controla o bloqueio da conta, a operação true informa que a conta está bloqueada, o ativo se torna false
            controlarBloqueioContaUsuario(usuario.getEmail(), true, false, null);
        }

        if (usuario.getContaBloqueada()) {
            throw new ContaBloqueada("Conta bloqueada. Aguarde aprovação de um administrador", null);
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmailUsuario(), loginDTO.getSenhaUsuario()
        );

        try {
            var auth = authenticationManager.authenticate(usernamePassword);
            var principal = (UsuarioDetails) auth.getPrincipal();

            // Ajusta para 0 o n° de tentativas incorretas se o login for bem sucedido!
            tentativaLoginIncorreto(usuario.getEmail(), 0);

            return tokenService.generateToken(principal);
        } catch (BadCredentialsException e) {
            // Atualização no banco de dados
            tentativaLoginIncorreto(usuario.getEmail(), usuario.getTentativasFalhas() + 1);

            // Neste momento estamos usando os valores em memória da consulta então precisamos adicionar mais 1
            if (usuario.getTentativasFalhas() + 1 < 5) {
                int tentativasRestantes = 5 - (usuario.getTentativasFalhas() + 1);
                String mensagemErro = String.format(
                        "Tentativa de login incorreto para o email: %s. " +
                        "Você tem %d tentativas restantes antes do bloqueio de sua conta!",
                        loginDTO.getEmailUsuario(), tentativasRestantes
                );
                throw new LoginIncorreto(mensagemErro, null);
            } else {
                // Controla o bloqueio da conta, a operação true informa que a conta está bloqueada, o ativo se mantém true
                // Isso ocorre se o usuário errar a senha 5 vezes
                controlarBloqueioContaUsuario(usuario.getEmail(), true, true, null);
                String mensagemErro = String.format(
                        "Você excedeu o número de tentativas incorretas! " +
                        "Conta bloqueada para o e-mail: %s",
                        loginDTO.getEmailUsuario()
                );
                throw new MaximoTentativasIncorretas(mensagemErro, null);
            }
        }
    }

    public UsuarioTokenDTO extrairDados(String token) {
        return tokenService.extrairDados(token);
    }

    public void updateSenha(UpdateSenhaDTO data, String authorization) {
        Optional<UsuarioDetails> usuarioOpt = usuariosService.selectUsuarioByEmail(data.getEmailUsuario(), authorization);
        UsuarioDetails usuario = usuarioOpt.get();
        UsuarioTokenDTO usuarioToken = getUsuarioByToken(authorization);

        if (!passwordEncoder.matches(data.getSenhaUsuario(), usuario.getPassword())) {
            throw new AlteracaoDeSenhaInvalida(
                    "Senha incorreta! É necessário inserir a senha correta antes de modificar",
                    usuarioToken
            );
        }

        if (passwordEncoder.matches(data.getNovaSenha(), usuario.getPassword())) {
            throw new AlteracaoDeSenhaInvalida(
                    "A nova senha não pode ser igual à senha atual",
                    usuarioToken
            );
        }

        if (data.getNovaSenha().isEmpty()) {
            throw new AlteracaoDeSenhaInvalida(
                    "Para alterar a sua senha é necessário informar uma nova senha",
                    usuarioToken
            );
        }

        if (!data.getNovaSenha().equals(data.getConfirmarNovaSenha())) {
            throw new AlteracaoDeSenhaInvalida(
                    "Confirmação de nova senha inválida! Verifique a diferença entre os campos",
                    usuarioToken
            );
        }

        data.setNovaSenha(passwordEncoder.encode(data.getNovaSenha()));

        authRepository.updateSenha(data);
    }

    public void tentativaLoginIncorreto(String emailUsuario, int tentativas) {
        authRepository.tentativaLoginIncorreto(emailUsuario, tentativas);
    }

    public void controlarBloqueioContaUsuario(String emailUsuario, boolean operacao, boolean ativo, String authorization) {
        if (authorization != null)
             autorizacaoControleBloqueio(authorization, operacao);

        tentativaLoginIncorreto(emailUsuario, 0);

        authRepository.controlarBloqueioContaUsuario(emailUsuario, operacao, ativo);
    }
}
