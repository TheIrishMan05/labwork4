package se.ifmo.lab4backend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenUtil {
    private final String SECRET_KEY;

    public JwtTokenUtil(@Value("${jwt.secret}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }


    private Key key;

    @PostConstruct
    public void initializeKey() {
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalArgumentException("JWT Secret Key is not defined in application.properties");
        }

        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Long userId, String username) {
        Date now = new Date();
        long expirationTime = 86400000;
        Date expiration = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<GrantedAuthority> getRolesFromToken(String token) {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);
    }
}
