package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Person;
import ru.yandex.practicum.filmorate.service.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final PersonService personService;

    @GetMapping
    List<Person> findAll() {
        return personService.getAll();
    }

    @PostMapping
    Person create(@Valid @RequestBody Person person) throws MethodArgumentNotValidException {
        return personService.create(person);
    }

    @PutMapping
    Person update(@Valid @RequestBody Person person) throws Exception{
        return personService.update(person);
    }
}
