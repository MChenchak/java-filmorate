package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final List<ApiError> errors;
}
