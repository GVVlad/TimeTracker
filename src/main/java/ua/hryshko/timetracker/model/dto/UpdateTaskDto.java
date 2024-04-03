package ua.hryshko.timetracker.model.dto;

import java.time.LocalTime;
import lombok.Data;

@Data
public class UpdateTaskDto {
    private Long taskId;
    private String title;
    private String description;
    private String status;
}
