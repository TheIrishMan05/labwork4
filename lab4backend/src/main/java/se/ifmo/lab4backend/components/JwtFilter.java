package se.ifmo.lab4backend.components;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.ifmo.lab4backend.services.JwtTokenUtil;

import java.io.IOException;
import java.util.List;


public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    public JwtFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);


            if (jwtTokenUtil.validateToken(token)) {
                setAuthenticationContext(token);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT токен невалидный или истекший");
            }
        }

        chain.doFilter(request, response);
    }

    private void setAuthenticationContext(String token) throws JwtException {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        List<GrantedAuthority> authorities = jwtTokenUtil.getRolesFromToken(token);

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth == null || currentAuth instanceof AnonymousAuthenticationToken) {
            CustomAuthentication authentication = new CustomAuthentication(username, userId, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
