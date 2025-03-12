package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.CommentRepository;

@ChangeLog
public class DatabaseChangelog {

    private int commentId = 1;

    @ChangeSet(order = "001", id = "addCommentForFirstBook", author = "bogun")
    public void addCommentForFirstBook(CommentRepository commentRepository) {
        final Author king = new Author("1", "Стивен Кинг");
        final Genre horrors = new Genre("1", "Ужасы");
        final Book pigPeppa = new Book("1", "Свинка Пеппа", king, horrors);

        commentRepository.save(new Comment(String.valueOf(commentId++),
                "Страшно", pigPeppa));
        commentRepository.save(new Comment(String.valueOf(commentId++),
                "Очень страшно", pigPeppa));
    }

    @ChangeSet(order = "002", id = "addCommentForSecondBook", author = "bogun")
    public void addCommentForSecondBook(CommentRepository commentRepository) {
        final Author govard = new Author("2", "Говард Лавкрафт");
        final Genre nightmares = new Genre("2", "Кошмары");
        final Book lambShon = new Book("2", "Барашек Шон", govard, nightmares);

        commentRepository.save(new Comment(String.valueOf(commentId++),
                "Мы не знаем, что это такое", lambShon));
    }

    @ChangeSet(order = "003", id = "addCommentForThirdBook", author = "bogun")
    public void addCommentForThirdBook(CommentRepository commentRepository) {
        final Author po = new Author("3", "Эдгар Аллан По");
        final Genre thrillers = new Genre("3", "Триллеры");
        final Book blueTractor = new Book("3", "Синий трактор", po, thrillers);

        commentRepository.save(new Comment(String.valueOf(commentId),
                "Если бы мы знали, что это такое, но мы не знаем, что это такое", blueTractor));
    }

    @ChangeSet(order = "004", id = "dropTables", author = "bogun", runAlways = true)
    public void dropTables(MongoDatabase database) {
        database.drop();
    }
}
