package com.milsondev.taskmanagement.api;

import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.UUID;

public class TaskDto {

    private UUID id;
    @NotEmpty(message = "Title should not be null")
    private String title;
    private Instant createdOn;
    private Instant updatedOn;
    @NotEmpty(message = "Expire Date cannot be empty, please inform!")
    private Instant expireOn;
    private Priority priority;
    private Status status;
    @NotEmpty(message = "Description is mandatory")
    private String description;

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

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Instant getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(Instant expireOn) {
        this.expireOn = expireOn;
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
}
