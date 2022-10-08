package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.FilmDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    String name;
    @Size(max = 200)
    String description;
    @FilmDate(message = "Дата релиза не может быть раньше 28/12/1895")
    LocalDate releaseDate;
    @Positive(message = "Длительность фильма не может быть меньше 0.")
    int duration;
}
