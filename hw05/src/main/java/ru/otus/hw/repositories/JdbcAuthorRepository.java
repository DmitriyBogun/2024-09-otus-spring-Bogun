package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcOperations operations;

    public JdbcAuthorRepository(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<Author> findAll() {
        return operations.getJdbcOperations()
                .query("select id, full_name from authors", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        List<Author> authors = operations
                .query("select id, full_name from authors where id = :id",
                        parameterSource, new AuthorRowMapper());
        return authors.stream().findAny();
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            final long id = resultSet.getLong("id");
            final String fullName = resultSet.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
