package org.pet.user.dao;

import lombok.RequiredArgsConstructor;
import org.pet.user.component.UserRowMapper;
import org.pet.user.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
@RequiredArgsConstructor
public class UserJDBCDataAccessService implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;
    @Override
    public List<User> selectAllUsers() {
        var sql = """
                SELECT id, name, email, age
                FROM user_table
                LIMIT 1000
                """;
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        var sql = """
                SELECT id, name, email, age
                FROM user_table
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertUser(User user) {
        var sql = """
                INSERT
                INTO user_table(name, email, age)
                VALUES (?,?,?)
                """;
        jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getAge());
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        Integer count;

        var sql = """
                SELECT COUNT(id)
                FROM user_table
                WHERE email = ?
                """;
        count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsUserWithId(Integer id) {
        Integer count;

        var sql = """
                SELECT COUNT(id)
                FROM user_table
                WHERE id = ?
                """;
        count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteUserById(Integer id) {
        var sql = """
                DELETE
                FROM user_table
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateUser(User user) {
        if (user.getName() != null) {
            String sql = "UPDATE user_table SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    user.getName(),
                    user.getId()
            );
            System.out.println("update user_table name result = " + result);
        }
        if (user.getAge() != null) {
            String sql = "UPDATE user_table SET age = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    user.getAge(),
                    user.getId()
            );
            System.out.println("update user_table age result = " + result);
        }
        if (user.getEmail() != null) {
            String sql = "UPDATE user_table SET email = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    user.getEmail(),
                    user.getId());
            System.out.println("update user_table email result = " + result);
        }
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return Optional.empty();
    }
}
