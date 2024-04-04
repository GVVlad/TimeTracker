package ua.hryshko.timetracker.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.hryshko.timetracker.enums.Status;
import ua.hryshko.timetracker.exceptions.AlreadyFinishedException;
import ua.hryshko.timetracker.exceptions.AlreadyStartedException;
import ua.hryshko.timetracker.exceptions.NotCorrectDataException;
import ua.hryshko.timetracker.exceptions.TaskNotFoundException;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;
import ua.hryshko.timetracker.repository.TaskRepository;
import ua.hryshko.timetracker.service.impl.TaskServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    private static final String TASK_TITLE = "Title";
    private static final String TASK_UPDATE_TITLE = "Update Title";

    private static final String TASK_DESCRIPTION = "Description";
    private static final String TASK_UPDATE_DESCRIPTION = "Update Description";

    private static final Long RIGHT_TASK_ID = 1L;
    private static final Long WRONG_TASK_ID = 100L;
    private static final LocalDateTime CREATED_TIME = LocalDateTime.now();
    private static final String STATUS = Status.CREATED.getName();
    private static final List<Task> tasks = new ArrayList<>();

    @Spy
    @InjectMocks
    private TaskServiceImpl testInstance;

    @Mock
    private TaskRepository taskRepository;
    private TaskDto taskDto;
    private Task task;
    private UpdateTaskDto updateTaskDto;

    @BeforeEach
    void setMocks() {
        task = getTask();
        taskDto = getTaskDto();
        updateTaskDto = getUpdateTaskDto();
        tasks.add(task);
    }

    @Test
    void testCreateTaskValid() {
        when(taskRepository.save(any())).thenReturn(task);
        Assertions.assertEquals(task, testInstance.createTask(taskDto));
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    public void testCreateTaskWrong() {
        taskDto.setTitle(null);
        Assertions.assertThrows(NotCorrectDataException.class, () -> {
            testInstance.createTask(taskDto);
        });

        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testUpdateTask(){
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        Task updatedTask = testInstance.updateTask(updateTaskDto);

        Assertions.assertEquals(task, updatedTask);
        Assertions.assertEquals(updateTaskDto.getTitle(), updatedTask.getTitle());
        Assertions.assertEquals(updateTaskDto.getDescription(), updatedTask.getDescription());
        Assertions.assertEquals(updateTaskDto.getStatus(), updatedTask.getStatus());
        verify(taskRepository, times(2)).findById(any());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateTaskNullTaskId() {
        updateTaskDto.setTaskId(WRONG_TASK_ID);

        Assertions.assertThrows(TaskNotFoundException.class, () -> {
            testInstance.updateTask(updateTaskDto);
        });

        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testStartTaskValid(){
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        Task startedTask = testInstance.startTask(1L);

        Assertions.assertEquals(task, startedTask);
        Assertions.assertEquals(Status.IN_PROGRESS.getName(), startedTask.getStatus());
        Assertions.assertNotNull(startedTask.getStartedAt());
        verify(taskRepository, times(2)).findById(any());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    public void testStartTaskWrong() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        task.setStatus(Status.IN_PROGRESS.getName());
        task.setStartedAt(LocalTime.now());

        Assertions.assertThrows(AlreadyStartedException.class, () -> {
            testInstance.startTask(RIGHT_TASK_ID);
        });

        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testFinishedTaskValid(){
        task.setStatus(Status.IN_PROGRESS.getName());
        task.setStartedAt(LocalTime.now().minus(15, ChronoUnit.MINUTES ));

        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        Task finishedTask = testInstance.finishedTask(1L);

        Assertions.assertEquals(task, finishedTask);
        Assertions.assertEquals(Status.FINISHED.getName(), finishedTask.getStatus());
        Assertions.assertNotNull(finishedTask.getFinishedAt());
        verify(taskRepository, times(2)).findById(any());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    public void testFinishedTaskWrong() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        task.setStatus(Status.FINISHED.getName());

        Assertions.assertThrows(AlreadyFinishedException.class, () -> {
            testInstance.finishedTask(RIGHT_TASK_ID);
        });

        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testGetTasksByStatusSuccessful() {
        when(taskRepository.findAllByStatus(anyString())).thenReturn(tasks);

        List<Task> tasksByStatus = testInstance.getTasksByStatus(Status.CREATED);

        Assertions.assertEquals(tasks, tasksByStatus);
        verify(taskRepository, times(1)).findAllByStatus(Status.CREATED.getName());
    }

    @Test
    public void testGetTasksByStatusNoTasksFound() {
        List<Task> emptyTasks = new ArrayList<>();

        when(taskRepository.findAllByStatus(anyString())).thenReturn(emptyTasks);

        List<Task> tasksByStatus = testInstance.getTasksByStatus(Status.CREATED);

        Assertions.assertTrue(tasksByStatus.isEmpty());
        verify(taskRepository, times(1)).findAllByStatus(Status.CREATED.getName());
    }

    @Test
    public void testGetTaskById(){
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        Task expected = testInstance.getTaskById(RIGHT_TASK_ID);

        Assertions.assertEquals(expected,task);
        verify(taskRepository,times(2)).findById(RIGHT_TASK_ID);
    }

    @Test
    public void testGetTaskByIdWrong(){
        when(taskRepository.findById(any())).thenReturn(Optional.empty());


        Assertions.assertThrows(TaskNotFoundException.class, () -> {
            testInstance.getTaskById(RIGHT_TASK_ID);
        });
    }

    @Test
    public void testDelete(){
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        testInstance.deleteTask(RIGHT_TASK_ID);

        verify(taskRepository,times(2)).findById(RIGHT_TASK_ID);
        verify(taskRepository,times(1)).delete(task);
    }

    @Test
    public void testGetAllTasks(){
        when(taskRepository.findAll()).thenReturn(tasks);

        Assertions.assertEquals(testInstance.getAllTask(),tasks);
        verify(taskRepository,times(1)).findAll();
    }

    @Test
    public void testDeleteAllFinishedTasks() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        List<Task> mockTasks = new ArrayList<>();
        task.setStatus(Status.FINISHED.getName());
        mockTasks.add(task);

        when(taskRepository.findAllByStatus(Status.FINISHED.getName())).thenReturn(mockTasks);

        testInstance.deleteAllFinishedTasks();

        verify(taskRepository, times(1)).findAllByStatus(Status.FINISHED.getName());
        verify(taskRepository, times(1)).delete(task);
    }

    private UpdateTaskDto getUpdateTaskDto(){
        return UpdateTaskDto.builder()
            .taskId(RIGHT_TASK_ID)
            .title(TASK_UPDATE_TITLE)
            .description(TASK_UPDATE_DESCRIPTION)
            .status(STATUS)
            .build();
    }

    private Task getTask() {
        return Task.builder()
            .id(RIGHT_TASK_ID)
            .title(TASK_TITLE)
            .description(TASK_DESCRIPTION)
            .createdAt(CREATED_TIME)
            .status(STATUS)
            .build();
    }

    private TaskDto getTaskDto() {

        return TaskDto.builder()
            .title(TASK_TITLE)
            .description(TASK_DESCRIPTION)
            .autoOpening(false)
            .build();
    }

}