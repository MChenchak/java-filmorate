package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbFilmStorage implements FilmStorage {
    private final FilmRepository filmRepository;

    @Override
    public Film create(Film data) {
        return filmRepository.save(data);
    }

    @Override
    public Film getById(Long id) {
        return filmRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("Фильм не найден"));
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film update(Film data) {
        if (!filmRepository.findById(data.getId()).isEmpty()) {
            return filmRepository.update(data);
        } else {
            throw new IllegalStateException("Фильм не найден");
        }
    }
}
