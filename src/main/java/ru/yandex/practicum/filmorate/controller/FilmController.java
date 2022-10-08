package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    @GetMapping
    List<Film> findAll() {
        return filmService.getAll();
    }

    @PostMapping
    Film create(@Valid @RequestBody Film film) throws MethodArgumentNotValidException{
        return filmService.create(film);
    }

    @PutMapping
    Film update(@Valid @RequestBody Film film) throws Exception{
        return filmService.update(film);
    }
}
