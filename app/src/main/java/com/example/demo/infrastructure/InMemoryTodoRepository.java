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
    public TodoElements getById(String id) {
        for (TodoElements theTodo : todo) {
            if (theTodo.getId().equals(id)) {
                return theTodo;
            }
        }
        return null;
    }

    @Override
    public void create(TodoElements todoElements) {
        todo.add(todoElements);
    }

    @Override
    public void save(TodoElements updatedTodo) {
        for (int i = 0; i < todo.size(); i++) {
            TodoElements existingTodo = todo.get(i);
            if (existingTodo.getId().equals(updatedTodo.getId())) {
                todo.set(i, updatedTodo); // 기존 항목을 새로운 항목으로 교체
                System.out.println(todo.get(i));
                System.out.println(updatedTodo.getTitle());
                return; // 업데이트 후 메서드 종료
            }
        }

    }


    @Override
    public void taskDone() {

    }

    @Override
    public void delete() {

    }
}
