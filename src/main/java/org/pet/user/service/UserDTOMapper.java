package org.pet.user.service;

import org.pet.user.dto.UserDTO;
import org.pet.user.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }
}
