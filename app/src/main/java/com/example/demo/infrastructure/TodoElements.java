package com.example.demo.infrastructure;

import java.time.LocalDateTime;

public class TodoElements {
    private String id;
    private String title;
    private String content;
    private boolean isDeleted;
    private boolean taskDone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public TodoElements(
            String id, String title,
            String content,
            boolean isDeleted,
            boolean taskDone,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.title = title;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public String getTitle() {
        return title;
    }

    public TodoElements updateTodo(String newTitle, String newContent) {
        return new TodoElements(
                this.id,
                newTitle,
                newContent,
                this.isDeleted,
                this.taskDone,
                this.createdAt,
                LocalDateTime.now(),
                this.deletedAt
        );
    }
    public TodoElements taskDone() {
        return new TodoElements(
                this.id,
                this.title,
                this.content,
                this.isDeleted,
                true,
                this.createdAt,
                this.updatedAt,
                this.deletedAt
        );
    }

    public TodoElements delete() {
        return new TodoElements(
                this.id,
                this.title,
                this.content,
                true,
                this.taskDone,
                this.createdAt,
                this.updatedAt,
                LocalDateTime.now()
        );
    }
}
