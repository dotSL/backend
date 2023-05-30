package org.pet.user.component;

import org.pet.user.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .age(rs.getInt("age"))
                .build();
    }
}
