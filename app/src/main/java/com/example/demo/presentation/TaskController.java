package com.example.demo.presentation;

import com.example.demo.application.Todo;
import com.example.demo.infrastructure.TodoElements;
import com.example.demo.presentation.dto.TodoDeletedResponseDto;
import com.example.demo.presentation.dto.TodoListResponseDto;
import com.example.demo.presentation.dto.TodoRequestDto;
import com.example.demo.presentation.dto.TodoResponseDto;
import com.example.demo.presentation.dto.TodoTaskDoneResponseDto;
import com.example.demo.presentation.dto.TodoUpdateRequestDto;
import com.example.demo.presentation.dto.TodoUpdatedResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final Todo todo;

    public TaskController(Todo todo) {
        this.todo = todo;
    }

    @GetMapping
    public TodoListResponseDto todoList() {
        List<TodoElements> todoList = todo.getTodoList();
        return TodoListResponseDto.of(todoList);
    }

    @GetMapping("/finish")
    public TodoListResponseDto finishedList() {
        List<TodoElements> todoList = todo.getFinishedList();
        return TodoListResponseDto.of(todoList);
    }

    @PostMapping
    public TodoResponseDto create(
            @RequestBody
            TodoRequestDto todoRequestDto
    ) {
        TodoElements todoElements = todo.createTask(
                todoRequestDto.title(), todoRequestDto.content());
        return new TodoResponseDto(
                todoElements.getId(),
                todoElements.getTitle(),
                todoElements.getContent(),
                todoElements.getCreatedAt()
        );
    }

    @PutMapping
    public TodoUpdatedResponseDto update(
            @RequestBody
            TodoUpdateRequestDto todoUpdateRequestDto
    ) {
        TodoElements todoElements = todo.update(
                todoUpdateRequestDto.id(),
                todoUpdateRequestDto.title(),
                todoUpdateRequestDto.content());
        return new TodoUpdatedResponseDto(
                todoElements.getId(),
                todoElements.getTitle(),
                todoElements.getContent(),
                todoElements.getCreatedAt()
        );
    }

    @PatchMapping("/{taskId}")
    public TodoTaskDoneResponseDto taskDone(
            @PathVariable String taskId
    ) {
        TodoElements todoTaskDoneResponse = todo.taskDone(taskId);
        return new TodoTaskDoneResponseDto(
                todoTaskDoneResponse.getId(),
                todoTaskDoneResponse.getTitle(),
                todoTaskDoneResponse.getContent(),
                todoTaskDoneResponse.getCreatedAt(),
                todoTaskDoneResponse.isTaskDone()
        );
    }

    @DeleteMapping("/{taskId}")
    public TodoDeletedResponseDto delete(
            @PathVariable String taskId
    ) {
        TodoElements todoTaskDoneResponse = todo.delete(taskId);
        return new TodoDeletedResponseDto(
                todoTaskDoneResponse.getId(),
                todoTaskDoneResponse.getTitle(),
                todoTaskDoneResponse.getContent(),
                todoTaskDoneResponse.getDeletedAt(),
                todoTaskDoneResponse.isDeleted()
        );
    }
}
