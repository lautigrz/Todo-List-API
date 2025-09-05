package com.app.mapper;

import com.app.controller.dto.TaskDTO;
import com.app.models.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO toDTO(TaskEntity task) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());

        return dto;
    }
}
