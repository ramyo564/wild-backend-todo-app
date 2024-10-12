package com.example.demo.presentation;

import com.example.demo.application.Todo;
import com.example.demo.infrastructure.TodoElements;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<TodoElements> list(){
        return todo.getTodoList();
    }
}
