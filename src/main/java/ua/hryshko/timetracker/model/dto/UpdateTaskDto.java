package ua.hryshko.timetracker.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTaskDto {
    private Long taskId;
    private String title;
    private String description;
    private String status;
}
