package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    List<User> findAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    User getById(@PathVariable Long id) {
        return userService.getById(id);
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
        return userService.create(user);
    }

    @PutMapping
    User update(@Valid @RequestBody User user) throws Exception{
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    void setFriend(@PathVariable("id") Long userId, @PathVariable Long friendId) {
        userService.setFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    void deleteFriend(@PathVariable("id") Long userId, @PathVariable Long friendId) {
        userService.deleteFriend(userId, friendId);
    }



}
