package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDao implements GenreRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> findById(int id) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "select * from genre where id = ?", id);
        if(rows.next()) {
            Genre genre = Genre.builder()
                    .id(rows.getLong("id"))
                    .name(rows.getString("name"))
                    .build();
            return Optional.of(genre);
        } else {
            throw new IllegalStateException("Genre not Found");
        }
    }

    @Override
    public List<Genre> findAll() {
        String query = "select * from genre";
        return jdbcTemplate.query(query, this::genreMapper);
    }

    @Override
    public List<Genre> findFilmGenres(Long filmId) {
        String query = "select genre_id as id, g.name from film_genre \n" +
                "join genre as g on film_genre.genre_id = g.id\n" +
                "where film_id = ?";
        return jdbcTemplate.query(query, this::genreMapper, filmId);
    }

    @Override
    public void updateFilmGenres(Long filmId, List<Genre> genres) {
        String query = "insert into film_genre values(?, ?)";
        for (Genre g : genres) {
            jdbcTemplate.update(query, filmId, g.getId());
        }
    }

    private Genre genreMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
