package com.example.demo.presentation;

import com.example.demo.application.Todo;
import com.example.demo.infrastructure.TodoElements;
import com.example.demo.presentation.dto.TodoListResponseDto;
import com.example.demo.presentation.dto.TodoRequestDto;
import com.example.demo.presentation.dto.TodoResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<TodoElements> list() {
        return todo.getTodoList();
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
}
