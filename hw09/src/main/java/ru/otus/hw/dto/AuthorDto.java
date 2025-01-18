package ru.otus.hw.dto;

import lombok.*;

@Data
@AllArgsConstructor
@ToString
public class AuthorDto {
    private Long id;

    private String fullName;
}
