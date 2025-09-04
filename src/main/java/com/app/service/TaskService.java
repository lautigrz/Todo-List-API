package com.app.service;

import com.app.controller.dto.TaskDTO;
import com.app.models.TaskEntity;
import com.app.models.UserEntity;
import com.app.repository.TaskRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public TaskEntity addTask(String username, TaskDTO task) {

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TaskEntity taskEntity = TaskEntity.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .user(user)
                .build();

       return taskRepository.save(taskEntity);
    }

    public List<TaskDTO> getAllTasks(String username) {
        return taskRepository.findTasksByUsername(username);
    }

    public TaskEntity getTask(String username, Long idTask) {

        return taskRepository.findByUsernameIdAndTaskId(username, idTask)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void deleteTask(String username, Long idTask) {
        TaskEntity task = taskRepository.findByUsernameIdAndTaskId(username, idTask)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        taskRepository.delete(task);
    }

    public TaskEntity updateTask(String username, Long idTask,TaskDTO task) {
        TaskEntity existingTask = taskRepository.findByUsernameIdAndTaskId(username, idTask)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());

        return taskRepository.save(existingTask);
    }

}
