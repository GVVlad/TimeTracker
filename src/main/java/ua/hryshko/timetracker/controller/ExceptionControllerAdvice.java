package ua.hryshko.timetracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.hryshko.timetracker.exceptions.AlreadyFinishedException;
import ua.hryshko.timetracker.exceptions.AlreadyStartedException;
import ua.hryshko.timetracker.exceptions.ErrorInformation;
import ua.hryshko.timetracker.exceptions.NotCorrectDataException;
import ua.hryshko.timetracker.exceptions.TaskNotFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorInformation> taskNotFoundException(Exception ex) {
        log.error("Task not found exception: ", ex);
        return getResponseBody(HttpStatus.NOT_FOUND, "Task not found. Wrong id or task doesn't exist");
    }

    @ExceptionHandler(AlreadyStartedException.class)
    public ResponseEntity<ErrorInformation> taskAlreadyStartedException(Exception ex) {
        log.error("Task was already started tracking exception: ", ex);
        return getResponseBody(HttpStatus.NOT_FOUND, "Task was already started tracking");
    }

    @ExceptionHandler(AlreadyFinishedException.class)
    public ResponseEntity<ErrorInformation> taskAlreadyFinishedException(Exception ex) {
        log.error("Task was already been tracked exception: ", ex);
        return getResponseBody(HttpStatus.NOT_FOUND, "Task was already been tracked");
    }

    @ExceptionHandler(NotCorrectDataException.class)
    public ResponseEntity<ErrorInformation> inputDataIncorrectException(Exception ex) {
        log.error("Input data was not correct exception: ", ex);
        return getResponseBody(HttpStatus.NOT_FOUND, "Input data for creating task was not correct");
    }

    private ResponseEntity<ErrorInformation> getResponseBody(HttpStatus status, String message) {
        return ResponseEntity.status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorInformation.of(message));
    }
}
