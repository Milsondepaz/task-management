package com.milsondev.taskmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milsondev.taskmanagement.api.TaskDto;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@ConditionalOnProperty(name = "task.data.file", matchIfMissing = false)
public class LoadData {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadData.class);

    @Value("${task.data.file}")
    private String taskData;

    private final ObjectMapper objectMapper;
    private final TaskService taskService;

    @Autowired
    public LoadData(final ObjectMapper objectMapper, final TaskService taskService) {
        this.objectMapper = objectMapper;
        this.taskService = taskService;
    }

    @PostConstruct
    public void init(){
        LOGGER.info("Importing tasks from file {}", taskData);
        try {
            final FileSystemResource jsonFile = new FileSystemResource(taskData);
            if (jsonFile.exists() && jsonFile.isFile()) {
                final List<TaskDto> taskDtoList = objectMapper.readValue(jsonFile.getFile(), new TypeReference<>() {});
                taskService.deleteAll();
                for (final TaskDto taskDto : taskDtoList) {
                    taskService.saveTask(taskDto);
                }
                LOGGER.info("imported Tasks from file {}", taskData);
            } else {
                LOGGER.error("File {} not found", taskData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}