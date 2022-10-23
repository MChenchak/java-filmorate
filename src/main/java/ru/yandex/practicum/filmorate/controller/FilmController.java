package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
  private final FilmService filmService;
  private final FilmStorage filmStorage;

  @GetMapping
  List<Film> findAll() {
    return filmStorage.getAll();
  }

  @GetMapping("/{id}")
  Film getById(@PathVariable Long id) {
    return filmStorage.getById(id);
  }

  @GetMapping("/popular")
  List<Film> getPopular(@RequestParam(value = "count", defaultValue = "10", required = false) int size) {
    return filmService.getPopular(size);
  }

  @PostMapping
  Film create(@Valid @RequestBody Film film) throws MethodArgumentNotValidException {
    return filmStorage.create(film);
  }

  @PutMapping
  Film update(@Valid @RequestBody Film film) throws Exception {
    return filmStorage.update(film);
  }

  @PutMapping("/{id}/like/{userId}")
  void likeFilm(@PathVariable("id") Long filmId, @PathVariable Long userId) {
    filmService.likeFilm(filmId, userId);
  }

  @DeleteMapping("/{id}/like/{userId}")
  void deleteFilm(@PathVariable("id") Long filmId, @PathVariable Long userId) {
    filmService.deleteLike(filmId, userId);
  }
}
