package com.qualiai.backend.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Order(1)
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";

//    @Value("${X-API-KEY}")
    private final String VALID_API_KEY = "777fb814655ed07d-e6d7d6ca-40564fad-b0e59c09-baf97df840f6-----f1cc4e8b967a";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);

        if (apiKey != null && apiKey.equals(VALID_API_KEY)) {
            // Cria a lista de roles ou authorities para esta API Key
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_API_KEY"));

            // Cria a autenticação usando a classe ApiKeyAuthentication
            Authentication authentication = new ApiKeyAuthentication(apiKey, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
