package org.pet.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
}
