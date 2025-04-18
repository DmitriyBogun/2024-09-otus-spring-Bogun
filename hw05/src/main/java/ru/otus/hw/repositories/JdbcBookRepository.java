package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotUpdateExceptions;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations operations;

    public JdbcBookRepository(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public Optional<Book> findById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        List<Book> books = operations.query("""
                select b.id, b.title, b.author_id, b.genre_id,
                        g.id, g.genre,
                        a.id, a.full_name
                 from books b 
                 inner join genres g 
                     on b.genre_id = g.id
                 inner join authors a
                     on b.author_id = a.id
                 where b.id = :id
                """, parameterSource, new BookRowMapper());

        if (books.size() == 1) {
            return Optional.of(books.get(0));
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return operations.getJdbcOperations().query(
                """
                        select b.id, b.title, b.author_id, b.genre_id,
                                g.id, g.genre,
                                a.id, a.full_name
                         from books b 
                         inner join genres g 
                             on b.genre_id = g.id
                         inner join authors a
                             on b.author_id = a.id
                        """, new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        operations.update(
                "delete from books where id = :id",
                parameterSource);
    }

    private Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("author_id", book.getAuthor().getId());
        parameterSource.addValue("genre_id", book.getGenre().getId());

        operations.update("""
                insert into books (title, author_id, genre_id)
                values (:title, :author_id, :genre_id)
                """, parameterSource, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", book.getId());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("author_id", book.getAuthor().getId());
        parameterSource.addValue("genre_id", book.getGenre().getId());
        int result = operations.update(
                """
                        update books 
                        set title = :title, author_id = :author_id, genre_id = :genre_id
                        where id = :id
                        """, parameterSource);
        if (result <= 0) {
            throw new EntityNotUpdateExceptions(book);
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(resultSet.getLong("authors.id"));
            author.setFullName(resultSet.getString("authors.full_name"));

            Genre genre = new Genre();
            genre.setId(resultSet.getLong("genres.id"));
            genre.setGenre(resultSet.getString("genres.genre"));

            Book book = new Book();
            book.setId(resultSet.getLong("books.id"));
            book.setTitle(resultSet.getString("books.title"));
            book.setAuthor(author);
            book.setGenre(genre);
            return book;
        }
    }
}
