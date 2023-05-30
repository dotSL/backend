package org.pet.user.dto;

public record UserRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
