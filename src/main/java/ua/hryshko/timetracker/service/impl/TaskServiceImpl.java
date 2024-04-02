package ua.hryshko.timetracker.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;
import ua.hryshko.timetracker.service.api.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public Task saveTask(TaskDto taskDto) {
        return null;
    }

    @Override
    public Task updateTask(UpdateTaskDto updateTaskDto) {
        return null;
    }

    @Override
    public void startTask(Long taskId) {

    }

    @Override
    public void finishedTask(Long taskId) {

    }

    @Override
    public void autoClose() {

    }

    @Override
    public List<Task> getAllTask() {
        return null;
    }

    @Override
    public Task getTaskById(Long taskId) {
        return null;
    }

    @Override
    public void deleteTask(Long taskId) {

    }
}
