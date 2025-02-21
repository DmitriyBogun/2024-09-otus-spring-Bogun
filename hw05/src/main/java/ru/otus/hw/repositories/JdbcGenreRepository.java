package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations operations;

    public JdbcGenreRepository(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<Genre> findAll() {
        return operations.getJdbcOperations().query("select id, genre from genres",
                new GenreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        List<Genre> genres = operations.query(
                "select id, genre from genres where id = :id",
                parameterSource, new GenreRowMapper());
        if (genres.size() == 1) {
            return Optional.of(genres.get(0));
        }
        return Optional.empty();
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            final long id = resultSet.getLong("id");
            final String genre = resultSet.getString("genre");
            return new Genre(id, genre);
        }
    }
}
