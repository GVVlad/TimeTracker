package ua.hryshko.timetracker.service.api;

import java.util.List;
import ua.hryshko.timetracker.enums.Status;
import ua.hryshko.timetracker.exceptions.AlreadyFinishedException;
import ua.hryshko.timetracker.exceptions.AlreadyStartedException;
import ua.hryshko.timetracker.exceptions.NotCorrectDataException;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;

/**
 * Service interface for managing tasks.
 */
public interface TaskService {

    /**
     * The method returns task {@code Task} if task successfully added.
     * @param taskDto - place body of dto {@code taskDto}
     * @return new {@code Task}.
     * @throws NotCorrectDataException if dto has incorrect data
     */
    Task createTask(TaskDto taskDto);

    /**
     * The method returns task {@code Task} if task successfully updated.
     * @param updateTaskDto - place body of dto {@code updateTaskDto}
     * @return new {@code Task}.
     * @throws NotCorrectDataException if dto for updating has incorrect data
     */
    Task updateTask(UpdateTaskDto updateTaskDto);

    /**
     * The method returns task {@code Task} if task successfully started tracking process.
     * @param taskId - put task id.
     * @return new {@code Task}.
     * @throws AlreadyStartedException if process of tracking was started before for this task.
     */
    Task startTask(Long taskId);

    /**
     * The method returns task {@code Task} if task successfully finished tracking process.
     * @param taskId - put task id.
     * @return new {@code Task}.
     * @throws AlreadyFinishedException if process of tracking was finished before for this task.
     */
    Task finishedTask(Long taskId);

    /**
     * The method returns list of tasks {@code List<Task>} of all tasks.
     *
     * @return new {@code List<Task>}.
     */
    List<Task> getAllTask();

    /**
     * The method returns list of tasks {@code List<Task>} which has inputted status.
     * @param status - put status.
     * @return new {@code List<Task>}.
     */
    List<Task> getTasksByStatus(Status status);

    /**
     * The method returns task {@code Task} which has inputted id.
     * @param taskId  - put task id.
     * @return new {@code Task}.
     */
    Task getTaskById(Long taskId);

    /**
     * The method delete task {@code List<Task>} which has inputted id.
     * @param taskId - put task id.
     */
    void deleteTask(Long taskId);

    /**
     * The method deleted all tasks {@code List<Task>} which has finished status.
     */
    void deleteAllFinishedTasks();
}
