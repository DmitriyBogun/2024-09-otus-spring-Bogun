package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookUpdateDto {

    private Long id;

    private String title;

    private Long authorId;

    private Long genreId;

    public static BookUpdateDto fromBookDto(BookDto bookDto) {
        return new BookUpdateDto(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor() == null ? null : bookDto.getAuthor().getId(),
                bookDto.getGenre() == null ? null : bookDto.getGenre().getId());
    }
}
