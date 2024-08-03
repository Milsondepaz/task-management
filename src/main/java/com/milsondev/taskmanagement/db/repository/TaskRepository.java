package com.milsondev.taskmanagement.db.repository;

import com.milsondev.taskmanagement.api.Status;
import com.milsondev.taskmanagement.db.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findAllByOrderByCreatedOnDesc();

    List<TaskEntity> findAllByStatusOrderByCreatedOnDesc(Status status);
}
