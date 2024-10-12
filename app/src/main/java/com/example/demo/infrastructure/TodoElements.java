package com.example.demo.infrastructure;

import java.util.Date;

public class TodoElements {
    private String id;
    private String content;
    private boolean isDeleted;
    private boolean taskDone;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public TodoElements(
            String id,
            String content,
            boolean isDeleted,
            boolean taskDone,
            Date createdAt,
            Date updatedAt,
            Date deletedAt
    ) {
        this.id = id;
        this.content = content;
        this.isDeleted = isDeleted;
        this.taskDone = taskDone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }
}
