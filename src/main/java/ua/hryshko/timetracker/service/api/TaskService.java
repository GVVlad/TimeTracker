package ua.hryshko.timetracker.service.api;

import java.util.List;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;

public interface TaskService {

    Task saveTask(TaskDto taskDto);
    Task updateTask(UpdateTaskDto updateTaskDto);
    void startTask(Long taskId);
    void finishedTask(Long taskId);
    void autoClose();
    List<Task> getAllTask();
    Task getTaskById(Long taskId);
    void deleteTask(Long taskId);
}
