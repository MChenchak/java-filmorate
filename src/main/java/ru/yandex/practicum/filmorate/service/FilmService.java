package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    public Film create(Film film) {
        return filmRepository.save(film);
    }

    public Film update(Film film) throws Exception {
        Film filmToUpdate = filmRepository.findById(film.getId());
        if (filmToUpdate != null) {
            filmToUpdate = film;
            filmRepository.save(filmToUpdate);
            return filmToUpdate;

        } else {
            throw new Exception("Фильм не найден");
        }


    }

}
