package se.ifmo.lab4backend.components;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.ifmo.lab4backend.services.JwtTokenUtil;

import java.io.IOException;

@Component
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;

    public JwtFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
            log.info("Token validation");

            if(jwtTokenUtil.validateToken(authHeader)) {
                log.info("Token validated. Setting context...");
                setAuthenticationContext(authHeader);
                log.info("Context set");
            } else {
                log.error("Invalid token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }


    public void setAuthenticationContext(String token) throws JwtException {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        CustomAuthentication authentication = new CustomAuthentication(username, userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
