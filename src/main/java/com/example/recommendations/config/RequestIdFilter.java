package com.example.recommendations.config;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestIdFilter extends HttpFilter {

    private static final String HEADER = "X-Request-ID";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String id = request.getHeader(HEADER);
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        response.setHeader(HEADER, id);
        chain.doFilter(request, response);
    }
}
