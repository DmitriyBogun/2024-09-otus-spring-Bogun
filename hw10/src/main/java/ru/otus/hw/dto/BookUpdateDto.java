package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class BookUpdateDto {

    private Long id;

    private String title;

    private Long authorId;

    private Long genreId;

}
