package ua.hryshko.timetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hryshko.timetracker.model.dto.TaskDto;
import ua.hryshko.timetracker.model.dto.UpdateTaskDto;
import ua.hryshko.timetracker.model.entity.Task;
import ua.hryshko.timetracker.service.api.TaskService;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create-task")
    public ResponseEntity<Task> addNewTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.createTask(taskDto));
    }

    @PostMapping(value = "/update-task")
    public ResponseEntity<Task> updateTask(@RequestBody UpdateTaskDto updateTaskDto) {
        return ResponseEntity.ok(taskService.updateTask(updateTaskDto));
    }

    @PostMapping(value = "/start-task-tracking")
    public ResponseEntity<Task> startTracking(@RequestParam("taskId")Long taskId) {
        return ResponseEntity.ok( taskService.startTask(taskId));
    }

    @PostMapping(value = "/finish-task-tracking")
    public ResponseEntity<Task> finishedTracking(@RequestParam("taskId")Long taskId) {
        return ResponseEntity.ok(taskService.finishedTask(taskId));
    }

    @DeleteMapping(value = "/delete-task")
    public ResponseEntity<Void> deleteTask(@RequestParam("taskId")Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete-finished-task")
    public ResponseEntity<Void> deleteFinishedTasks() {
        taskService.deleteAllFinishedTasks();
        return ResponseEntity.ok().build();
    }





    // Some test comment
    @GetMapping(value = "/get-tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTask());

    }
}
