package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.annotation.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {
    private Long id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200)
    private String description;
    @FilmDate(message = "Дата релиза не может быть раньше 28/12/1895")
    private LocalDate releaseDate;
    @Positive(message = "Длительность фильма не может быть меньше 0.")
    private int duration;
    Set<User> likes = new HashSet<>();
    private List<Genre> genres;
    private Mpa mpa;
    private int rate;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("description", description);
        map.put("release_date", releaseDate);
        map.put("duration", duration);
        map.put("mpa_id",mpa.getId());
        map.put("rate", rate);
        return map;
    }
}
