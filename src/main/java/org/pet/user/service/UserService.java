package org.pet.user.service;

import org.pet.excpetion.DuplicateResourceException;
import org.pet.excpetion.RequestValidationException;
import org.pet.excpetion.ResourceNotFoundException;
import org.pet.user.dao.UserDao;
import org.pet.user.dto.UserDTO;
import org.pet.user.dto.UserRegistrationRequest;
import org.pet.user.dto.UserUpdateRequest;
import org.pet.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDao userDao;
    private final UserDTOMapper userDTOMapper;

    public UserService(@Qualifier("jdbc") UserDao userDao, UserDTOMapper userDTOMapper) {
        this.userDao = userDao;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> getAllUsers()
    {
        return userDao.selectAllUsers()
                .stream().map(userDTOMapper)
                .collect(Collectors.toList());

    }

    public UserDTO getUser(Integer id)
    {
        return userDao.selectUserById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user with id [%s] not found".formatted(id)
                ));
    }

    public void addUser(UserRegistrationRequest userRegistrationRequest)
    {
        String email = userRegistrationRequest.email();
        if(userDao.existsUserWithEmail(email))
            throw new DuplicateResourceException(
                    "email already taken."
            );
        User user = User.builder()
                .name(userRegistrationRequest.name())
                .email(userRegistrationRequest.email())
                .age(userRegistrationRequest.age())
                .build();
        userDao.insertUser(user);
    }

    public void updateUser(Integer id, UserUpdateRequest updateRequest)
    {
        User user = userDao.selectUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user with id [%s] not found".formatted(id)
                ));
        boolean changes = false;

        if(updateRequest.name() != null && !updateRequest.name().equals(user.getName()))
        {
            user.setName(updateRequest.name());
            changes = true;
        }
        if(updateRequest.age() != null && !updateRequest.age().equals(user.getAge()))
        {
            user.setAge(updateRequest.age());
            changes = true;
        }
        if(updateRequest.email() != null && !updateRequest.email().equals(user.getEmail()))
        {
            if(userDao.existsUserWithEmail(updateRequest.email()))
                throw new DuplicateResourceException(
                        "email already taken"
                );
            user.setEmail(updateRequest.email());
            changes = true;
        }

        if(!changes)
            throw new RequestValidationException("no data changes found.");

        userDao.insertUser(user);
    }

    public void deleteUser(Integer id)
    {
        userDao.deleteUserById(id);
    }
}
