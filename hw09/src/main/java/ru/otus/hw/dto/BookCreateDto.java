package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto {
    private Long id;

    @NotBlank(message = "Book title can't be null")
    @Size(min = 1, max = 100, message = "Book title should be with size from 1 to 100 symbols")
    private String title;

    @NotBlank(message = "Author name is can't be null")
    private String authorName;

    @NotBlank(message = "Genre can't be null")
    private String genre;
}
