package com.milsondev.taskmanagement.api;

public enum Status {
    Done("Done"),
    Ready("Ready"),
    Progress("Progress");
    private String description;
    Status (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
