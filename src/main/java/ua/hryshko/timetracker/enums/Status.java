package ua.hryshko.timetracker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    CREATED("Created"),
    IN_PROGRESS("Progress"),
    FINISHED("Finished");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
