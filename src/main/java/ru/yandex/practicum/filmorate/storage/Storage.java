package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface Storage<T> {
    T create(T data);
    T getById(Long id);
    List<T> getAll();
    T update(T data);
}
