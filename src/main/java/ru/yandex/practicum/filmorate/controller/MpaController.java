package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.MpaRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaRepository mpaRepository;

    @GetMapping("/{id}")
    Mpa getById(@PathVariable int id) {
        return mpaRepository.findById(id).get();
    }

    @GetMapping
    public List<Mpa> findAll() {
       return mpaRepository.findAll();
    }
}
