package ua.hryshko.timetracker.service.api;

import java.util.List;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;

public interface TaskService {

    Task createTask(TaskDto taskDto);
    Task updateTask(UpdateTaskDto updateTaskDto);
    Task startTask(Long taskId);
    Task finishedTask(Long taskId);
    void autoClose();
    List<Task> getAllTask();
    Task getTaskById(Long taskId);
    void deleteTask(Long taskId);
}
