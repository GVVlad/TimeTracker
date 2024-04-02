package ua.hryshko.timetracker.model.mapper;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import ua.hryshko.timetracker.enums.Status;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.entity.Task;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskMapper {

    public static Task personDtoToPerson(TaskDto taskDto) {
        ModelMapper modelMapper = new ModelMapper();

        Task task = modelMapper.map(taskDto, Task.class);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(Status.CREATED.getName());

        return task;
    }

    public static void updatePersonFromDto(TaskDto taskDto, Task task) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(taskDto, task);
    }
}
