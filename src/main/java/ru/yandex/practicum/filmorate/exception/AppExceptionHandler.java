package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class, IllegalStateException.class})
  @ResponseBody
  public ResponseEntity<ErrorResponse> handle(Exception exception) {
    if (exception instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException exc = (MethodArgumentNotValidException) exception;
      return handleMethodArgumentNotValidException(exc);
    } else if (exception instanceof IllegalStateException) {
      IllegalStateException exc = (IllegalStateException) exception;
      return handleIllegalStateException(exc);
    } else {
      return handleInternalException();
    }
  }

  private ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException exc) {
    final List<ApiError> errors =
        List.of(new ApiError(exc.getMessage()));
    errors.forEach(apiError -> log.error(apiError.getMessage()));
    return sendResponse(new ErrorResponse(errors), HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    final List<ApiError> errors =
        exception.getBindingResult().getFieldErrors().stream()
            .map(error -> new ApiError(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());
    errors.forEach(apiError -> log.error(apiError.getMessage()));
    return sendResponse(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
  }

  @Nullable
  private ResponseEntity<ErrorResponse> handleInternalException() {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }




  protected ResponseEntity<ErrorResponse> sendResponse(ErrorResponse body, HttpStatus httpStatus) {
    return new ResponseEntity<>(body, httpStatus);
  }
}
