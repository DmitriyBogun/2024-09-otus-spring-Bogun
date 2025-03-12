package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UserDto {

    @NotNull
    private Long id;

    @NotBlank(message = "User username can not be empty!")
    private String username;

    @NotBlank(message = "User password can not be empty!")
    private String password;

    private Boolean enabled;
}
