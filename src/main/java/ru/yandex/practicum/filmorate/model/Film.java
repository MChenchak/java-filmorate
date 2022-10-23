package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.annotation.FilmDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    String name;
    @Size(max = 200)
    String description;
    @FilmDate(message = "Дата релиза не может быть раньше 28/12/1895")
    LocalDate releaseDate;
    @Positive(message = "Длительность фильма не может быть меньше 0.")
    int duration;
    @ManyToMany()
    @JoinTable(
            name = "film_likes",
            joinColumns = { @JoinColumn(name = "film_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    Set<User> likes = new HashSet<>();
}
