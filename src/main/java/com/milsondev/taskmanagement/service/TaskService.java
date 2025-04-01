package com.milsondev.taskmanagement.service;

import com.milsondev.taskmanagement.api.Priority;
import com.milsondev.taskmanagement.api.Status;
import com.milsondev.taskmanagement.api.TaskDto;
import com.milsondev.taskmanagement.db.entity.TaskEntity;
import com.milsondev.taskmanagement.db.repository.TaskRepository;
import com.milsondev.taskmanagement.exceptions.TaskNotFoundExceptions;
import com.milsondev.taskmanagement.convert.TaskConvert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
                taskEntity.setExpireOn(taskConvert.convertStringToInstant(taskDto.getExpireOn()));
                taskEntity.setStatus(taskDto.getStatus());
                taskEntity.setPriority(taskDto.getPriority());
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

    public List<String> getPriorities() {
        return Arrays.asList(Priority.Low.toString(), Priority.High.toString(), Priority.Normal.toString());
    }

    public List<String> getStatus() {
        return Arrays.asList(Status.Done.toString(), Status.Ready.toString(), Status.Progress.toString());
    }

    public List<TaskDto> getTaskListByStatus(String strStatus) {
        Status status = taskConvert.convertStatus(strStatus);
        if(status == null) {
            return getTaskList ();
        }
        List<TaskEntity> taskEntityList = taskRepository.findAllByStatusOrderByCreatedOnDesc(status);
        return taskEntityList.stream().map(taskConvert::convertTaskEntityToTaskDto).collect(Collectors.toList());
    }

    public Page<TaskDto> getTaskListPaginated(int pageNo, int pageSize, String status) {

        int qtd = taskRepository.findAll().size();

        List<TaskDto> taskDtoList;
        Page<TaskDto> page;
        if(status == null || status.isEmpty() || status.equals("all")){
            taskDtoList = getTaskList();
        } else {
            taskDtoList = getTaskListByStatus(status);
            pageNo = 1;
        }
        if(!taskDtoList.isEmpty()) {
            if(taskDtoList.size() < pageSize) {
                pageSize = taskDtoList.size();
            }
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
            int start = (int ) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), taskDtoList.size());
            List<TaskDto> subList = taskDtoList.subList(start, end);
            page = new PageImpl<>(subList, pageable, taskDtoList.size());
        } else {
            page = Page.empty();
        }
        return page;
    }


    public void deleteAll() {
        taskRepository.deleteAll();
    }
}
