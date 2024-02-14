package com.payhere.homework.api.application.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public JwtAuthenticationFilter(final JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = getToken(request);
        if (token != null) {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }


    private Authentication getAuthentication(final String token) {
        Jws<Claims> parse = jwtProvider.parse(token);
        Claims body = parse.getBody();

        if (jwtProvider.checkTokenExpired(body.getExpiration())) {
            throw new ExpiredJwtException(parse.getHeader(), body, "만료 된 토큰입니다. 토큰을 갱신해주세요.");
        }

        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        String role = String.valueOf(body.get("role"));
        roles.add(new SimpleGrantedAuthority("ROLE_" + role));

        Long id = Long.valueOf(body.getSubject());
        String userId = body.get("userId", String.class);
        String name = body.get("name", String.class);
        MemberContext memberContextMapper = new MemberContext(id, userId, name, roles.stream().toList());

        return new JwtAuthentication(memberContextMapper, token, roles);
    }

    private String getToken(final ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(AUTHORIZATION);

        if (token == null || !token.contains(TOKEN_PREFIX)) {
            return null;
        }
        return token.substring("Bearer ".length());
    }

}
