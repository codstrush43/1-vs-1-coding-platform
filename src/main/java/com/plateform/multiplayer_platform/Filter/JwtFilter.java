package com.plateform.multiplayer_platform.Filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.plateform.multiplayer_platform.JWTUtil.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter{

    private JwtUtil jwtUtil;
    JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader("Authorization");

        String jwtToken = null;

        if (token != null && token.startsWith("Bearer ")) {
            jwtToken = token.substring(7);
        }
        else
        {
            chain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.validateToken(jwtToken);

        if (username == null) {
            httpResponse.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Invalid or expired JWT"
            );
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}