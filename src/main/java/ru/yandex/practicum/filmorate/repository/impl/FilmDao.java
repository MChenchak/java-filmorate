package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FilmDao implements FilmRepository {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;
    private final MpaDao mpaDao;

    @Autowired
    public FilmDao(JdbcTemplate jdbcTemplate, GenreDao genreDao, MpaDao mpaDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
        this.mpaDao = mpaDao;
    }

    @Override
    public List<Film> findAll() {
        String query = "select * from film";
        return jdbcTemplate.query(query, this::filmMapper);
    }

    @Override
    public Film save(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("id");

        Long filmId = simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();

        if (!CollectionUtils.isEmpty(film.getGenres())) {
            genreDao.updateFilmGenres(filmId, film.getGenres());
        }

        return findById(filmId).get();
    }

    @Override
    public Film update(Film film) {
        findById(film.getId());
        String query = "update film set \n" +
                "    name = ?,\n" +
                "    description = ?,\n" +
                "    release_date = ?,\n" +
                "    duration = ?,\n" +
                "    mpa_id = ?,\n" +
                "    rate = ?\n" +
                "where id = ?";

        jdbcTemplate.update(query,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getRate(),
                film.getId());

        if (film.getGenres() != null) {
            if (film.getGenres().isEmpty()) {
                deleteFilmGenre(film.getId());
            } else {
                deleteFilmGenre(film.getId());
                genreDao.updateFilmGenres(film.getId(), film.getGenres());
            }
        }

        return findById(film.getId()).get();
    }

    private void deleteFilmGenre(Long filmId) {
        String deleteQuery = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(deleteQuery, filmId);
    }

    @Override
    public Optional<Film> findById(Long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(
                "select * from film where id = ?", id);

        if (filmRows.next()) {
            Film film = Film.builder()
                    .id(filmRows.getLong("id"))
                    .name(filmRows.getString("name"))
                    .description(filmRows.getString("description"))
                    .releaseDate(filmRows.getDate("release_date").toLocalDate())
                    .duration(filmRows.getInt("duration"))
                    .genres(genreDao.findFilmGenres(filmRows.getLong("id")))
                    .mpa(mpaDao.findById(filmRows.getInt("mpa_id")).get())
                    .rate(filmRows.getInt("rate"))
                    .build();
            return Optional.of(film);
        } else {
            throw new IllegalStateException("Film not Found");
        }
    }

    @Override
    public void likeFilm(Long filmId, Long userId) {
        String query = "insert into film_likes values(?, ?)";
        jdbcTemplate.update(query, userId, filmId);
        updateFilmRate(filmId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        if (findFilmLike(filmId, userId)) {
            String deleteQuery = "delete from film_likes where film_id = ?" +
                    "and user_id = ?";
            jdbcTemplate.update(deleteQuery, filmId, userId);
            updateFilmRate(filmId);
        } else {
            throw new IllegalStateException("Like not found");
        }
    }

    private boolean findFilmLike(Long filmId, Long userId) {
        String query = "select * from film_likes where film_id = ?" +
                "and user_id = ?";
        return !jdbcTemplate.queryForList(query, filmId, userId).isEmpty();
    }

    private void updateFilmRate(Long filmId) {
        String query = "select * from film_likes where film_id = ?";
        int rate = jdbcTemplate.queryForList(query, filmId).size();

        String updateQuery = "update film set rate = ? where id = ?";
        jdbcTemplate.update(updateQuery, rate, filmId);

    }

    private Film filmMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .genres(genreDao.findFilmGenres(resultSet.getLong("id")))
                .mpa(mpaDao.findById(resultSet.getInt("mpa_id")).get())
                .rate(resultSet.getInt("rate"))
                .build();

    }
}
