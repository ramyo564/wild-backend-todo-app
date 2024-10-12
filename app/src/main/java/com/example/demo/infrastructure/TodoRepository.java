package com.example.demo.infrastructure;

import java.util.List;

public interface TodoRepository {
    List<TodoElements> getAll();

    TodoElements getById(String id);

    void create(TodoElements todoElements);

    void save(TodoElements updatedTodo);

    void taskDone();

    void delete();
}
