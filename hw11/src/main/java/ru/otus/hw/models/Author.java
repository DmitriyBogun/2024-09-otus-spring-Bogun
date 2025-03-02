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
@Document(value = "authors")
public class Author {

    @Id
    private String id;

    @NotNull
    private String fullName;

    @PersistenceCreator
    public Author(String id, @NotNull String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
