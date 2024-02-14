package com.payhere.homework.api.application.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.homework.api.presentation.common.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.payhere.homework.api.application.constants.ExceptionConstants.*;


public class JwtAuthenticationExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType("application", "json", StandardCharsets.UTF_8);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            String errorMessage = getErrorMessage(e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_UTF8.toString());
            ErrorResponse errorResponse = ErrorResponse.of(errorMessage);
            objectMapper.writeValue(response.getWriter(), errorResponse);
        }
    }

    private String getErrorMessage(Exception e) {
        if (e instanceof MalformedJwtException) {
            return MALFORMED_JWT_EXCEPTION_MESSAGE;
        } else if (e instanceof ExpiredJwtException) {
            return EXPIRED_JWT_EXCEPTION_MESSAGE;
        } else {
            return DEFAULT_JWT_EXCEPTION_MESSAGE;
        }
    }

}
