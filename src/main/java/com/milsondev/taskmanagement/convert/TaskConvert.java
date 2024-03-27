package com.milsondev.taskmanagement.convert;

import com.milsondev.taskmanagement.api.TaskDto;
import com.milsondev.taskmanagement.db.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskConvert {

    public TaskEntity convertTaskDtoToTaskEntity(final TaskDto taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setStatus(taskDto.getStatus());
        taskEntity.setCreatedOn(taskDto.getCreatedOn());
        taskEntity.setExpireOn(taskDto.getExpireOn());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setUpdatedOn(taskDto.getUpdatedOn());
        return taskEntity;
    }

    public TaskDto convertTaskEntityToTaskDto(final TaskEntity taskEntity) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setTitle(taskEntity.getTitle());
        taskDto.setPriority(taskEntity.getPriority());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setStatus(taskEntity.getStatus());
        taskDto.setCreatedOn(taskEntity.getCreatedOn());
        taskDto.setExpireOn(taskEntity.getExpireOn());
        taskDto.setUpdatedOn(taskEntity.getUpdatedOn());
        return taskDto;
    }
}
