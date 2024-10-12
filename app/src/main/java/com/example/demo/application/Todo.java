package com.example.demo.application;

import com.example.demo.infrastructure.TodoElements;
import com.example.demo.infrastructure.TodoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Todo {
    private final TodoRepository todoRepository;

    public Todo(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoElements> getTodoList(){
        return todoRepository.getAll();
    }
}
