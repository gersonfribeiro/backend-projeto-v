package com.qualiai.backend.auth;

import com.qualiai.backend.crud.domain.usuarios.UsuarioDetails;
import com.qualiai.backend.crud.domain.usuarios.UsuariosRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Order(2)
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuariosRepository usuariosRepository;

    public SecurityFilter(TokenService tokenService, UsuariosRepository usuariosRepository) {
        this.tokenService = tokenService;
        this.usuariosRepository = usuariosRepository;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var subject = tokenService.validateToken(token);
            Optional<UsuarioDetails> usuario = usuariosRepository.selectUsuarioByEmail(subject);

            if (usuario.isPresent()) {
                UsuarioDetails usuarioDetails = usuario.get();
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuarioDetails, null, usuarioDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
