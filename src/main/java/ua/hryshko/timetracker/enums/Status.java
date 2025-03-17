package ua.hryshko.timetracker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Status {

    CREATED("Created"),
    IN_PROGRESS("Progress"),
    FINISHED("Finished");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
