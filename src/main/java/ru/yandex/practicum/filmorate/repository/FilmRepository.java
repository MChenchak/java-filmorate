package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findAll();

    Film save(Film film);

    Optional<Film> findById(Long id);

}
