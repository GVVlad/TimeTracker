package ua.hryshko.timetracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;
import ua.hryshko.timetracker.service.api.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create new task ", responses = {
        @ApiResponse(responseCode = "200", description = "New task was successfully created"),
        @ApiResponse(responseCode = "404", description = "Task's data incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/create-task")
    public ResponseEntity<Task> addNewTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.createTask(taskDto));
    }

    @Operation(summary = "Update task ", responses = {
        @ApiResponse(responseCode = "200", description = "Task was successfully updated "),
        @ApiResponse(responseCode = "404", description = "Task's data incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "update-task")
    public ResponseEntity<Task> updateTask(@RequestBody UpdateTaskDto updateTaskDto) {
        return ResponseEntity.ok(taskService.updateTask(updateTaskDto));
    }

    @Operation(summary = "Start task tracking ", responses = {
        @ApiResponse(responseCode = "200", description = "Task was successfully started tracking "),
        @ApiResponse(responseCode = "404", description = "Task's data incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "start-task-tracking")
    public ResponseEntity<Task> startTracking(@RequestParam("taskId")Long taskId) {
        return ResponseEntity.ok( taskService.startTask(taskId));
    }

    @Operation(summary = "Finished task tracking ", responses = {
        @ApiResponse(responseCode = "200", description = "Task was successfully finished tracking "),
        @ApiResponse(responseCode = "404", description = "Task's data incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "finish-task-tracking")
    public ResponseEntity<Task> finishedTracking(@RequestParam("taskId")Long taskId) {
        return ResponseEntity.ok(taskService.finishedTask(taskId));
    }

    @Operation(summary = "Delete task ", responses = {
        @ApiResponse(responseCode = "200", description = "Task was successfully deleted "),
        @ApiResponse(responseCode = "404", description = "Task's data incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(value = "delete-task")
    public ResponseEntity<Void> deleteTask(@RequestParam("taskId")Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete all finished tasks ", responses = {
        @ApiResponse(responseCode = "200", description = "Finished tasks was successfully deleted "),
        @ApiResponse(responseCode = "404", description = "Task's data incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(value = "delete-finished-task")
    public ResponseEntity<Void> deleteFinishedTasks() {
        taskService.deleteAllFinishedTasks();
        return ResponseEntity.ok().build();
    }
}
