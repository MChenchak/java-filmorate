package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User{
    private Long id;
    @NotNull
    @Pattern(regexp = "^\\w[a-zA-Z@#0-9.]*$", message = "Логин не должен содержать пробелы.")
    private String login;
    private String name;
    @Email
    private String email;
    @Past
    private LocalDate birthday;

    @JsonIgnore
    private Set<User> subscriptions = new HashSet<>();

    @JsonIgnore
    private Set<User> subscribers = new HashSet<>();

    @JsonIgnore
    private Set<Film> likedFilms = new HashSet<>();

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("login", login);
        map.put("name", name);
        map.put("email", email);
        map.put("birthday", birthday);
        return map;
    }
}
