package com.app.controller;

import com.app.controller.dto.TaskDTO;
import com.app.models.TaskEntity;
import com.app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@Valid @RequestBody TaskDTO task, Authentication auth) {

        String username = auth.getName();
       TaskEntity taskEntity = taskService.addTask(username, task);
        Map<String, Object> objectsMap = Map.of(
                "id", taskEntity.getId(),
                "title", taskEntity.getTitle(),
                "description", taskEntity.getDescription()
        );

        return ResponseEntity.ok(objectsMap);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getTasksAll")
    public ResponseEntity<?> getAllTasks(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(taskService.getAllTasks(username));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getTask")
    public ResponseEntity<?> getTask(Long idTask, Authentication auth) {
        String username = auth.getName();

        TaskEntity taskEntity = taskService.getTask(username, idTask);
        Map<String, Object> objectsMap = Map.of(
                "id", taskEntity.getId(),
                "title", taskEntity.getTitle(),
                "description", taskEntity.getDescription()
        );

        return ResponseEntity.ok(objectsMap);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/deleteTask/{idTask}")
    public ResponseEntity<?> deleteTask(@PathVariable Long idTask, Authentication auth) {
        String username = auth.getName();
        taskService.deleteTask(username, idTask);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateTask/{idTask}")
    public ResponseEntity<?> updateTask(@PathVariable Long idTask, @Valid @RequestBody TaskDTO task, Authentication auth) {
        String username = auth.getName();
        TaskEntity updatedTask = taskService.updateTask(username, idTask, task);
        Map<String, Object> objectsMap = Map.of(
                "id", updatedTask.getId(),
                "title", updatedTask.getTitle(),
                "description", updatedTask.getDescription()
        );

        return ResponseEntity.ok(objectsMap);
    }


}
