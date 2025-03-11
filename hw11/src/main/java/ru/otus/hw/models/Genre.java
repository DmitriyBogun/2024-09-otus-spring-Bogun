package ru.otus.hw.models;

import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Document(value = "genres")
public class Genre {

    @Id
    private String id;

    @NotNull
    private String genre;

    @PersistenceCreator
    public Genre(String id, @NotNull String genre) {
        this.id = id;
        this.genre = genre;
    }
}
