package ru.yandex.practicum.filmorate.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
  private final FilmRepository filmRepository;
  private final UserService userService;

  public List<Film> getAll() {
    return filmRepository.findAll();
  }

  public Film create(Film film) {
    return filmRepository.save(film);
  }

  public Film update(Film film) throws Exception {
    Film filmToUpdate = getById(film.getId());
    filmToUpdate = film;
    filmRepository.save(filmToUpdate);
    return filmToUpdate;
  }

  public Film getById(@NonNull Long id) {
    return filmRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("Фильм не найден"));
  }


  public void likeFilm(Long filmId, Long userId) {
    Film film = getById(filmId);
    User user = userService.getById(userId);

    Set<User> likeUsers = film.getLikes();
    likeUsers.add(user);
    film.setLikes(likeUsers);

    filmRepository.save(film);
  }

  public void deleteLike(Long filmId, Long userId) {
    Film film = getById(filmId);
    Set<User> likeUsers = film.getLikes();

    likeUsers.remove(userService.getById(userId));

    filmRepository.save(film);

  }

  public List<Film> getPopular(int size) {
    return filmRepository.findAll().stream()
            .sorted(Comparator.comparing(film ->film.getLikes().size(), Comparator.reverseOrder()))
            .limit(size)
            .collect(Collectors.toList());
  }
}
