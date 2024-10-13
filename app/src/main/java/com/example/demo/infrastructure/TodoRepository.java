package com.example.demo.infrastructure;

import java.util.List;

public interface TodoRepository {
    List<TodoElements> getAll();

    TodoElements getById(String id);

    void create(TodoElements todoElements);

    TodoElements save(TodoElements updatedTodo);

}
