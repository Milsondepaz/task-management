package com.milsondev.taskmanagement.api;

public enum Priority {
    High("High"),
    Low("Low"),
    Normal("Normal");
    private String description;
    Priority (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
