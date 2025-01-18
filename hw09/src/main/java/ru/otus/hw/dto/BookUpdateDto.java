package ru.otus.hw.dto;

import lombok.*;

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
