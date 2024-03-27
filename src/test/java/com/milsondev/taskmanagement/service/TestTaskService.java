package com.milsondev.taskmanagement.service;

import com.milsondev.taskmanagement.api.Priority;
import com.milsondev.taskmanagement.api.Status;
import com.milsondev.taskmanagement.api.TaskDto;
import com.milsondev.taskmanagement.db.entity.TaskEntity;
import com.milsondev.taskmanagement.db.repository.TaskRepository;
import com.milsondev.taskmanagement.convert.TaskConvert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TestTaskService {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskConvert taskConvert;
    @InjectMocks
    private TaskService taskService;

    public TestTaskService() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveTask() {
        final TaskDto taskDto = new TaskDto();
        final TaskEntity taskEntity = new TaskEntity();

        when(taskConvert.convertTaskDtoToTaskEntity(taskDto)).thenReturn(taskEntity);
        taskService.saveTask(taskDto);

        verify(taskConvert, times(1)).convertTaskDtoToTaskEntity(taskDto);
        verify(taskRepository, times(1)).save(taskEntity);
    }

    @Test
    public void testGetTaskById() {
        UUID id = UUID.randomUUID();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));
        TaskDto taskDto = new TaskDto();
        when(taskConvert.convertTaskEntityToTaskDto(taskEntity)).thenReturn(taskDto);

        TaskDto taskDtoResult = taskService.getTaskById(id);

        assertNotNull(taskDtoResult);
        assertEquals(taskDto, taskDtoResult);

        verify(taskRepository, times(1)).findById(id);
        verify(taskConvert, times(1)).convertTaskEntityToTaskDto(taskEntity);
    }

    @Test
    public void testDeleteTask() {
        UUID id = UUID.randomUUID();
        taskService.deleteTask(id);
        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(UUID.randomUUID());
        taskDto.setTitle("Title Updated");
        Instant date = Instant.now();
        taskDto.setUpdatedOn(date);
        taskDto.setExpireOn(date);
        taskDto.setCreatedOn(date);
        taskDto.setStatus(Status.Done);
        taskDto.setDescription("Description Updated");
        taskDto.setPriority(Priority.High);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskDto.getId());
        when(taskRepository.findById(taskDto.getId())).thenReturn(Optional.of(taskEntity));

        taskService.updateTask(taskDto);

        verify(taskRepository, times(1)).findById(taskDto.getId());
        verify(taskRepository, times(1)).save(taskEntity);

        assertEquals("Title Updated", taskEntity.getTitle());
        assertEquals("Description Updated", taskEntity.getDescription());
        assertEquals(Status.Done, taskEntity.getStatus());
        assertEquals(Priority.High, taskEntity.getPriority());
        assertEquals(date, taskEntity.getUpdatedOn());
        assertEquals(date, taskEntity.getCreatedOn());
        assertEquals(date, taskEntity.getExpireOn());
    }

    @Test
    public void testGetTaskList() {
        List<TaskEntity> taskEntityList = Arrays.asList(new TaskEntity(), new TaskEntity(), new TaskEntity());
        when(taskRepository.findAllByOrderByCreatedOnDesc()).thenReturn(taskEntityList);

        TaskDto taskDto = new TaskDto();
        TaskEntity taskEntity = new TaskEntity();
        when(taskConvert.convertTaskEntityToTaskDto(taskEntity)).thenReturn(taskDto);

        List<TaskDto> taskDtoList = taskService.getTaskList();

        assertNotNull(taskDtoList);
        assertEquals(3, taskDtoList.size());
        verify(taskRepository, times(1)).findAllByOrderByCreatedOnDesc();
        verify(taskConvert, times(3)).convertTaskEntityToTaskDto((TaskEntity) any());
    }
}
