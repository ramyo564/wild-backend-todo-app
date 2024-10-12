package com.example.demo.infrastructure;

import java.util.List;

public interface TodoRepository {
    List<TodoElements> getAll();

    void create();

    void edit();

    void taskDone();

    void delete();
}
