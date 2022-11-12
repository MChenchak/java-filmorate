package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User readUser(long id);

    List<User> readAllFriends(long userId);

    User updateUser(User user);

    User deleteUser(User user);

    List<User> readAllUsers();

    boolean idNotExist(long id);
}
