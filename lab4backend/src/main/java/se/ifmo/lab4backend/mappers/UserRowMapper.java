package se.ifmo.lab4backend.mappers;

import org.springframework.jdbc.core.RowMapper;
import se.ifmo.lab4backend.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User foundUser = new User();
        foundUser.setId(rs.getLong("id"));
        foundUser.setUsername(rs.getString("username"));
        foundUser.setPassword(rs.getString("password"));
        return foundUser;
    }
}
