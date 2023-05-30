package org.pet.user.dto;

public record UserUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
