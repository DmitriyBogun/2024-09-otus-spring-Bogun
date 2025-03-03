package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ToString
public class BookUpdateDto {

    @NotNull
    private String id;

    private String title;

    private String authorId;

    private String genreId;

}
