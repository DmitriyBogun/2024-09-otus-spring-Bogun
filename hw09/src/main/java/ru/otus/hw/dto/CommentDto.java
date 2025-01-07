package ru.otus.hw.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;

    private String text;

    private Long bookId;
}
