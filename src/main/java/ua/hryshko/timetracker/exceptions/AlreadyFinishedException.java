package ua.hryshko.timetracker.exceptions;

public class AlreadyFinishedException extends RuntimeException{
    public AlreadyFinishedException(String message) {
        super(message);
    }
}
