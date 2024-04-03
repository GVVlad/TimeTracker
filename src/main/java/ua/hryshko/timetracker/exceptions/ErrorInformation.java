package ua.hryshko.timetracker.exceptions;

import lombok.Data;

@Data
public class ErrorInformation {
    private String message;

    public static ErrorInformation of(String errorMessage) {
        ErrorInformation result = new ErrorInformation();
        result.message = errorMessage;
        return result;
    }
}
