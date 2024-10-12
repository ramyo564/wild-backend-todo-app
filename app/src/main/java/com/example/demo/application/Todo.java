package com.example.demo.application;

import com.example.demo.infrastructure.TodoElements;
import com.example.demo.infrastructure.TodoRepository;
import com.example.demo.presentation.dto.TodoResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class Todo {
    private final TodoRepository todoRepository;

    public Todo(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoElements> getTodoList(){
        return todoRepository.getAll();
    }

    public TodoElements createTask(String title, String content) {
        String id = UUID.randomUUID().toString().substring(0,8);
        TodoElements todoElements = new TodoElements(
                id,
                title,
                content,
                false,
                false,
                LocalDateTime.now(),
                null,
                null
        );
        todoRepository.create(todoElements);
        return todoElements;
    }
}
