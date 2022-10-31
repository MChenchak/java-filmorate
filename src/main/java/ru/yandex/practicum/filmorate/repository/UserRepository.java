package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    List<User> findAll();

    List<User> findUsersBySubscribers(User user);

    User save(User user);

    User update(User user);

    Optional<User> findById(Long id);

}
