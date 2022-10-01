package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;


public class FilmDateValidator implements ConstraintValidator<FilmDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.isAfter(LocalDate.parse("28-12-1895", dtf));
    }
}
