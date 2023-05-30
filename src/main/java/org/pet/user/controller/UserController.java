package org.pet.user.controller;

import org.pet.user.dto.UserDTO;
import org.pet.user.dto.UserRegistrationRequest;
import org.pet.user.dto.UserUpdateRequest;
import org.pet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("{user-id}")
    public UserDTO getUserById(@PathVariable("user-id") Integer id)
    {
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest)
    {
        userService.addUser(userRegistrationRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{customer-id}")
    public void deleteUser(@PathVariable("customer-id") Integer id)
    {
        userService.deleteUser(id);
    }

    @PutMapping("{user-id}")
    public void updateUser(@PathVariable("user-id") Integer userId,
                           @RequestBody UserUpdateRequest userUpdateRequest)
    {
        userService.updateUser(userId, userUpdateRequest);
    }
}
