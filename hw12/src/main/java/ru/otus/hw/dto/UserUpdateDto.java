package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDto {

    @NotNull
    private Long id;

    @NotBlank(message = "User username can not be null")
    @Size(min = 1, max = 100, message = "User username should be with " +
            "size from 1 to 100 symbols")
    private String username;

    @NotNull(message = "User password can not be null")
    @Size(min = 1, max = 100, message = "User password should be with " +
            "size from 1 to 100 symbols")
    private String password;

    private Boolean enabled;
}
