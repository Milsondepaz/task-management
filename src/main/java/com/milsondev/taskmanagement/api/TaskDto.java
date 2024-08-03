package com.milsondev.taskmanagement.api;

import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.UUID;

public class TaskDto {

    private UUID id;
    @NotEmpty(message = "Title should not be null")
    private String title;
    private String createdOn;
    private String updatedOn;
    @NotEmpty(message = "Expire Date cannot be empty, please inform!")
    private String expireOn;
    private Priority priority;
    private Status status;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    private String statusClass;
    private String priorityClass;

    public String getStatusClass() {
        return statusClass;
    }

    public void setStatusClass(String statusClass) {
        this.statusClass = statusClass;
    }

    public String getPriorityClass() {
        return priorityClass;
    }

    public void setPriorityClass(String priorityClass) {
        this.priorityClass = priorityClass;
    }

    public TaskDto(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public @NotEmpty(message = "Expire Date cannot be empty, please inform!") String getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(@NotEmpty(message = "Expire Date cannot be empty, please inform!") String expireOn) {
        this.expireOn = expireOn;
    }
}
