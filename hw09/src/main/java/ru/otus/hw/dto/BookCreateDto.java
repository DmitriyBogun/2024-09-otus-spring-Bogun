package ru.otus.hw.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto {
    private Long id;

    private String title;

    private String authorName;

    private String genre;
}
