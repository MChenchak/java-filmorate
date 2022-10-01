package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Person;
import ru.yandex.practicum.filmorate.repository.PersonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person create(Person person) {
        if (person.getName() == null) {
            person.setName(person.getLogin());
        }
        return personRepository.save(person);
    }

    public Person update(Person person) throws Exception{
        Person userToUpdate = personRepository.findById(person.getId());
        if (userToUpdate != null) {
            userToUpdate = person;
            personRepository.save(userToUpdate);
            return userToUpdate;
        } else {
            throw new Exception("Пользователь не найден");
        }
    }
}
