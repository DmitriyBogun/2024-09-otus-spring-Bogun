package ru.otus.hw.models;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Document(value = "books")
public class Book {

    @Id
    private String id;

    @NotNull
    private String title;

    @NotNull
    private Author author;

    @NotNull
    private Genre genre;

    @PersistenceCreator
    public Book(String id, @NotNull String title,
                @NotNull Author author, @NotNull Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}
