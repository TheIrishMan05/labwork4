package se.ifmo.weblab4.components;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class CustomAuthentication extends UsernamePasswordAuthenticationToken {
    private Long userId;

    public CustomAuthentication(String principal, Long userId) {
        super(principal, null);
        this.userId = userId;
    }


}
