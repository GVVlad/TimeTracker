package ua.hryshko.timetracker.exceptions;

public class AlreadyStartedException extends RuntimeException{
    public AlreadyStartedException(String message) {
        super(message);
    }
}
