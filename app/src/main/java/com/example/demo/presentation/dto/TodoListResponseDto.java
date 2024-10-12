package com.example.demo.presentation.dto;

import com.example.demo.infrastructure.TodoElements;

import java.util.List;
import java.util.stream.Collectors;

public record TodoListResponseDto(List<TodoResponseDto> todoList) {

    public static TodoListResponseDto of(List<TodoElements> todoElements) {
        List<TodoResponseDto> responseList =
                todoElements.stream()
                        .map(todo ->
                                new TodoResponseDto(
                                        todo.getId(),
                                        todo.getTitle(),
                                        todo.getContent(),
                                        todo.getCreatedAt()
                                )
                        )
                        .collect(Collectors.toList());

        return new TodoListResponseDto(responseList);
    }
}
