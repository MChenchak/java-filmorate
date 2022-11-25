package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.impl.FilmDao;
import ru.yandex.practicum.filmorate.storage.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final DbFilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmDao filmDao;


    public void likeFilm(Long filmId, Long userId) {
        filmDao.likeFilm(filmId, userId);
    }

    public Film update(Film film) {

        if (film.getGenres() != null) {
            List<Genre> distinctGenres = film.getGenres().stream()
                    .distinct()
                    .collect(Collectors.toList());
            film.setGenres(distinctGenres);
        }
        return filmStorage.update(film);
    }

    public void deleteLike(Long filmId, Long userId) {
        filmDao.deleteLike(filmId, userId);
    }

    public List<Film> getPopular(int size) {
        return filmDao.findAll().stream()
                .sorted(Comparator.comparing(film -> film.getRate()))
                .limit(size)
                .collect(Collectors.toList());
    }
}
