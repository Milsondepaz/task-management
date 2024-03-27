package com.milsondev.taskmanagement.service;

import com.milsondev.taskmanagement.api.TaskDto;
import com.milsondev.taskmanagement.db.entity.TaskEntity;
import com.milsondev.taskmanagement.db.repository.TaskRepository;
import com.milsondev.taskmanagement.exceptions.TaskNotFoundExceptions;
import com.milsondev.taskmanagement.convert.TaskConvert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskConvert taskConvert;

    public TaskService(final TaskRepository taskRepository, final TaskConvert taskConvert) {
        this.taskRepository = taskRepository;
        this.taskConvert = taskConvert;
    }

    public void saveTask(final TaskDto taskDto) {
        try {
            final TaskEntity taskEntity = taskConvert.convertTaskDtoToTaskEntity(taskDto);
            taskRepository.save(taskEntity);
        } catch (RuntimeException re) {
            throw  re;
        }
    }

    public TaskDto getTaskById(final UUID id) {
        final Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if(optionalTaskEntity.isPresent()) {
            return taskConvert.convertTaskEntityToTaskDto(optionalTaskEntity.get());
        } else {
            throw new TaskNotFoundExceptions("Task with id " + id + " not found");
        }
    }

    public void deleteTask(final UUID id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(final TaskDto taskDto) {
        try {
            final Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(taskDto.getId());
            if (optionalTaskEntity.isPresent()) {
                TaskEntity taskEntity = optionalTaskEntity.get();
                taskEntity.setDescription(taskDto.getDescription());
                taskEntity.setTitle(taskDto.getTitle());
                taskEntity.setUpdatedOn(taskDto.getUpdatedOn());
                taskEntity.setExpireOn(taskDto.getExpireOn());
                taskEntity.setStatus(taskDto.getStatus());
                taskEntity.setPriority(taskDto.getPriority());
                taskEntity.setCreatedOn(taskDto.getCreatedOn());
                taskRepository.save(taskEntity);
            } else {
                throw new TaskNotFoundExceptions("Task with id " + taskDto.getDescription() + " not found");
            }
        } catch (final RuntimeException re) {
            throw re;
        }
    }

    public List<TaskDto> getTaskList () {
        return taskRepository.findAllByOrderByCreatedOnDesc()
                .stream()
                .map(taskConvert::convertTaskEntityToTaskDto)
                .collect(Collectors.toList());
    }
}
