package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbUserStorage implements UserStorage {
    private final UserRepository userRepository;
    @Override
    public User create(User data) {
        if (data.getName().isEmpty()) {
            data.setName(data.getLogin());
        }
        return userRepository.save(data);
    }

    @Override
    public User getById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User data) {
        User userToUpdate = getById(data.getId());
        userToUpdate = data;
        userRepository.save(userToUpdate);
        return userToUpdate;
    }
}
