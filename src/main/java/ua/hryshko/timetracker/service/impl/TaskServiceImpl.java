package ua.hryshko.timetracker.service.impl;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.hryshko.timetracker.enums.Status;
import ua.hryshko.timetracker.exceptions.AlreadyFinishedException;
import ua.hryshko.timetracker.exceptions.AlreadyStartedException;
import ua.hryshko.timetracker.exceptions.NotCorrectDataException;
import ua.hryshko.timetracker.exceptions.TaskNotFoundException;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;
import ua.hryshko.timetracker.model.mapper.TaskMapper;
import ua.hryshko.timetracker.repository.TaskRepository;
import ua.hryshko.timetracker.service.api.TaskService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(TaskDto taskDto) {
        if(taskDto.getTitle() != null) {
            Task task = taskRepository.save(TaskMapper.personDtoToPerson(taskDto));
            log.info("Task was successfully created: " + task);
            return task;
        } else {
            throw new NotCorrectDataException("Task wasn't created. Wrong input data");
        }
    }

    @Override
    public Task updateTask(UpdateTaskDto updateTaskDto) {
        Task task = getTaskById(updateTaskDto.getTaskId());

        if(updateTaskDto.getTitle() != null) {
            task.setTitle(updateTaskDto.getTitle());
            log.info(String.format("Task's title with id = %s was updated. New title: %s", task.getId(),
                task.getTitle()));
        }
        if(updateTaskDto.getDescription() != null) {
            task.setDescription(updateTaskDto.getDescription());
            log.info(String.format("Task's description with id = %s was updated. New description: %s", task.getId(),
                task.getDescription()));
        }
        if(updateTaskDto.getStatus() != null) {
            task.setStatus(updateTaskDto.getStatus());
            log.info(String.format("Task's status with id = %s was updated. New status: %s", task.getId(),
                task.getStatus()));
        }
        if(updateTaskDto.getTitle() == null && updateTaskDto.getDescription() == null &&
            updateTaskDto.getStatus() == null) {
            log.info(String.format("Task's status with id = %s nothing to update", task.getId()));
        }
        task = taskRepository.save(task);

        log.info("Task was successfully updated: " + task);
        return task;
    }

    @Override
    public Task startTask(Long taskId) {
        Task task = getTaskById(taskId);
        if(task.getStatus().equals(Status.CREATED.getName()) && task.getStartedAt() == null) {
            task.setStartedAt(LocalTime.now());
            task.setStatus(Status.IN_PROGRESS.getName());

            task = taskRepository.save(task);
            log.info(String.format("Task with id = %s was started tracking at %s", task.getId(), task.getStartedAt()));
            return task;
        } else {
            throw new AlreadyStartedException(String.format("Task with id= %s was already started tracking", taskId));
        }

    }

    @Override
    public Task finishedTask(Long taskId) {
        Task task = getTaskById(taskId);

        if(task.getStatus().equals(Status.IN_PROGRESS.getName()) && task.getStartedAt() != null) {

            task.setFinishedAt(LocalTime.now());
            Duration duration = Duration.between(task.getStartedAt(), LocalTime.now());
            task.setSpentTime(String.format("%s:%s:%s", duration.toHours(), duration.toMinutes(), duration.toSeconds() % 60));
            task.setStatus(Status.FINISHED.getName());

            task = taskRepository.save(task);
            log.info(String.format("Task with id= %s tracking has been completed at %s. Time was spent: %s", task.getId(),
                task.getStartedAt(), task.getSpentTime()));

            return task;
        } else {
            throw new AlreadyFinishedException(String.format("Task with id= %s has already been tracked", taskId));
        }
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
        if(taskRepository.findById(taskId).isPresent()) {
            return taskRepository.findById(taskId).get();
        } else {
            throw new TaskNotFoundException(String.format("Task with id = %s not found", taskId));
        }
    }

    @Override
    public void deleteTask(Long taskId) {

    }
}
