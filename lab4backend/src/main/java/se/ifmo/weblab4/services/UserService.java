package se.ifmo.weblab4.services;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.weblab4.mappers.UserRowMapper;
import se.ifmo.weblab4.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private JwtTokenUtil jwtTokenUtil;
    private NamedParameterJdbcTemplate template;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(JwtTokenUtil jwtTokenUtil, NamedParameterJdbcTemplate template) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.template = template;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";
        SqlParameterSource source = new MapSqlParameterSource().addValue("username", username);

        List<User> users = template.query(sql, source, new UserRowMapper());

        return users.stream().findFirst();
    }


    @Transactional
    public void registerUser(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            throw new BadCredentialsException("Username is already in use");
        }
        Long newId = template.queryForObject("SELECT user_seq.NEXTVAL FROM DUAL", new MapSqlParameterSource(), Long.class);
        String sql = "INSERT INTO users (id, username, password) values (:id, :username, :password)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", newId)
                .addValue("username", user.getUsername())
                .addValue("password", bCryptPasswordEncoder.encode(user.getPassword()));
        template.update(sql, source);
    }

    public Optional<String> login(User user) {
        Optional<User> userOptional = findByUsername(user.getUsername());
        return userOptional.map(account -> jwtTokenUtil.generateToken(account.getId(), account.getUsername()));
    }




}
