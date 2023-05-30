package org.pet.user.dao;

import org.pet.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> selectAllUsers();
    Optional<User> selectUserById(Integer id);
    void insertUser(User user);
    boolean existsUserWithEmail(String email);
    boolean existsUserWithId(Integer id);
    void deleteUserById(Integer id);
    void updateUser(User user);
    Optional<User> selectUserByEmail(String email);
}
