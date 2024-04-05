package ua.hryshko.timetracker.controller;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.hryshko.timetracker.enums.Status;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;
import ua.hryshko.timetracker.service.api.TaskService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    private static final String TASK_TITLE = "Title";
    private static final String TASK_UPDATE_TITLE = "Update Title";

    private static final String TASK_DESCRIPTION = "Description";
    private static final String TASK_UPDATE_DESCRIPTION = "Update Description";

    private static final Long RIGHT_TASK_ID = 1L;
    private static final Long WRONG_TASK_ID = 100L;
    private static final LocalDateTime CREATED_TIME = LocalDateTime.now();
    private static final String STATUS = Status.CREATED.getName();

    @Mock
    private TaskService taskService;

    @Spy
    @InjectMocks
    private TaskController testInstance;

    private TaskDto taskDto;
    private Task task;
    private UpdateTaskDto updateTaskDto;

    @BeforeEach
    void setMocks() {
       taskDto = getTaskDto();
       task = getTask();
       updateTaskDto= getUpdateTaskDto();
    }


    @Test
    @DisplayName("addNewTask should return StatusCode = OK")
    public void testCreateTask() {
        when(taskService.createTask(any())).thenReturn(task);
        ResponseEntity<Task> responseEntity = testInstance.addNewTask(taskDto);

        assertEquals(task,responseEntity.getBody());
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(taskService).createTask(taskDto);
    }

    @Test
    @DisplayName("updateTask should return StatusCode = OK")
    public void testUpdateTask() {
        ResponseEntity<Task> responseEntity = testInstance.updateTask(updateTaskDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(taskService).updateTask(updateTaskDto);
    }

    @Test
    @DisplayName("startTracking should return StatusCode = OK")
    public void testStartedTrackingTask() {
        ResponseEntity<Task> responseEntity = testInstance.startTracking(RIGHT_TASK_ID);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(taskService).startTask(RIGHT_TASK_ID);
    }

    @Test
    @DisplayName("finishedTracking should return StatusCode = OK")
    public void testFinishedTrackingTask() {
        ResponseEntity<Task> responseEntity = testInstance.finishedTracking(RIGHT_TASK_ID);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(taskService).finishedTask(RIGHT_TASK_ID);
    }

    @Test
    @DisplayName("finishedTracking should return StatusCode = OK")
    public void testDeleteTask() {
        ResponseEntity<Void> responseEntity = testInstance.deleteTask(RIGHT_TASK_ID);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(taskService).deleteTask(RIGHT_TASK_ID);
    }

    @Test
    @DisplayName("finishedTracking should return StatusCode = OK")
    public void testDeleteFinishedTasks() {
        ResponseEntity<Void> responseEntity = testInstance.deleteFinishedTasks();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(taskService).deleteAllFinishedTasks();
    }

    private TaskDto getTaskDto() {

        return TaskDto.builder()
            .title(TASK_TITLE)
            .description(TASK_DESCRIPTION)
            .autoOpening(false)
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

    private UpdateTaskDto getUpdateTaskDto(){
        return UpdateTaskDto.builder()
            .taskId(RIGHT_TASK_ID)
            .title(TASK_UPDATE_TITLE)
            .description(TASK_UPDATE_DESCRIPTION)
            .status(STATUS)
            .build();
    }

}