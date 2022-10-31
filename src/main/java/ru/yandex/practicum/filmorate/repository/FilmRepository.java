package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository {
    List<Film> findAll();

    Film save(Film film);

    Film update(Film film);

    Optional<Film> findById(Long id);

    void likeFilm(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
