package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "person")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    @Pattern(regexp = "^\\w[a-zA-Z@#0-9.]*$", message = "Логин не должен содержать пробелы.")
    String login;
    String name;
    @Email
    String email;
    @Past
    LocalDate birthday;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = { @JoinColumn(name = "subscriber_id") },
            inverseJoinColumns = { @JoinColumn(name = "subscription_id") }

    )
    Set<User> subscriptions = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "subscriptions")
    Set<User> subscribers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "likes")
    Set<Film> likedFilms = new HashSet<>();
}
