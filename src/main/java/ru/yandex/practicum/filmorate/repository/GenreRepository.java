package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findById(int id);

    List<Genre> findAll();

    List<Genre> findFilmGenres(Long filmId);

    void updateFilmGenres(Long filmId, List<Genre> genres);
}
