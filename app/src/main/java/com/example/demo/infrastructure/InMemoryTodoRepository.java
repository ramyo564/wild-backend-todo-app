package com.example.demo.infrastructure;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryTodoRepository implements TodoRepository {
    private final List<TodoElements> todo = new ArrayList<>();

    @Override
    public List<TodoElements> getAll() {
        return new ArrayList<>(todo);
    }

    @Override
    public void create(TodoElements todoElements) {
        todo.add(todoElements);
    }

    @Override
    public void edit() {

    }

    @Override
    public void taskDone() {

    }

    @Override
    public void delete() {

    }
}
