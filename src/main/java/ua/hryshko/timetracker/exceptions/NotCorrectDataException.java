package ua.hryshko.timetracker.exceptions;

public class NotCorrectDataException extends RuntimeException{
    public NotCorrectDataException(String message) {
        super(message);
    }
}
