package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserStorage userStorage;

    @GetMapping
    List<User> findAll() {
        return userStorage.getAll();
    }

    @GetMapping("/{id}")
    User getById(@PathVariable Long id) {
        return userStorage.getById(id);
    }

    @GetMapping("/{id}/friends")
    List<User> getFriends(@PathVariable("id") Long userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    List<User> getCommonFriends(@PathVariable("id") Long userId, @PathVariable("otherId") Long otherUserId) {
        return userService.getCommonFriends(userId, otherUserId);
    }

    @PostMapping
    User create(@Valid @RequestBody User user) {
        return userStorage.create(user);
    }

    @PutMapping
    User update(@Valid @RequestBody User user) throws Exception{
        return userStorage.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    void addFriend(
            @PathVariable("id") Long userId,
            @PathVariable Long friendId) {
        if(userId < 0 || friendId < 0) {
            throw new IllegalStateException("Указан некорректный id");
        }
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    void deleteFriend(@PathVariable("id") Long userId, @PathVariable Long friendId) {
        userService.deleteFriend(userId, friendId);
    }



}
