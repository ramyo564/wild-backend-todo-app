package com.example.demo.infrastructure;

import java.util.List;

public interface TodoRepository {
    List<TodoElements> getAll();

    void create(TodoElements todoElements);

    void edit();

    void taskDone();

    void delete();
}
