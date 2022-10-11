package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService{
  private final DbFilmStorage filmStorage;
  private final UserStorage userStorage;


  public void likeFilm(Long filmId, Long userId) {
    Film film = filmStorage.getById(filmId);
    User user = userStorage.getById(userId);

    Set<User> likeUsers = film.getLikes();
    likeUsers.add(user);
    film.setLikes(likeUsers);

    filmStorage.create(film);
  }

  public void deleteLike(Long filmId, Long userId) {
    Film film = filmStorage.getById(filmId);
    Set<User> likeUsers = film.getLikes();

    likeUsers.remove(userStorage.getById(userId));

    filmStorage.create(film);
  }

  public List<Film> getPopular(int size) {
    return filmStorage.getAll().stream()
            .sorted(Comparator.comparing(film ->film.getLikes().size(), Comparator.reverseOrder()))
            .limit(size)
            .collect(Collectors.toList());
  }
}
