package com.app.repository;

import com.app.controller.dto.TaskDTO;
import com.app.models.TaskEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

    @Query("SELECT new com.app.controller.dto.TaskDTO(t.title, t.description) " +
            "FROM TaskEntity t WHERE t.user.username = :username")
    List<TaskDTO> findTasksByUsername(@Param("username") String username);


    @Query("SELECT t FROM TaskEntity t WHERE t.user.username = :username AND t.id = :taskId")
    Optional<TaskEntity> findByUsernameIdAndTaskId(@Param("username") String username,
                                               @Param("taskId") Long taskId);

}
