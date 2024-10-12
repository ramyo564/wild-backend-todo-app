package com.example.demo.application;

import com.example.demo.infrastructure.TodoElements;
import com.example.demo.infrastructure.TodoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class Todo {
    private final TodoRepository todoRepository;

    public Todo(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoElements> getTodoList() {
        return todoRepository.getAll().stream().filter(todoElement -> !todoElement.isTaskDone())
                .collect(Collectors.toList());
    }

    public List<TodoElements> getFilishedList() {
        return todoRepository.getAll().stream().filter(TodoElements::isTaskDone)
                .collect(Collectors.toList());
    }

    public TodoElements createTask(String title, String content) {
        String id = UUID.randomUUID().toString().substring(0, 8);
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

    public TodoElements update(String id, String title, String content) {
        TodoElements todo = todoRepository.getById(id);

        todoRepository.save(todo.updateTodo(title, content));
        return todo.updateTodo(title, content);
    }

}
