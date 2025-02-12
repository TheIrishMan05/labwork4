package se.ifmo.lab4backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.lab4backend.models.User;
import se.ifmo.lab4backend.repositories.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private JwtTokenUtil jwtTokenUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserService(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    public void registerUser(User user) throws BadCredentialsException {
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new BadCredentialsException("Username is already taken");
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("Saving user: " + newUser);
        userRepository.save(newUser);
        System.out.println("User saved!");
    }

    public Optional<String> login(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        return userOptional.map(account -> jwtTokenUtil.generateToken(account.getId(), account.getUsername()));
    }




}
