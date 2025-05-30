package ua.hryshko.timetracker.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {

    private String title;
    private String description;
    private Boolean autoOpening;
}
