package com.example.demo.application;

import com.example.demo.exception.TodoLimitExceededException;
import com.example.demo.infrastructure.TodoElements;
import com.example.demo.infrastructure.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoTest {
    private Todo todo;
    private TodoRepository todoRepository;
    private List<TodoElements> testTodoList;

    @BeforeEach
    void setUp() {
        todoRepository = mock(TodoRepository.class);
        todo = new Todo(todoRepository);
        testTodoList = new ArrayList<>(List.of(
                new TodoElements(
                        "id1",
                        "todo1",
                        "content1",
                        false,
                        false,
                        LocalDateTime.now(),
                        null,
                        null),
                new TodoElements("id2",
                        "todo2",
                        "content2",
                        false,
                        false,
                        LocalDateTime.now(),
                        null,
                        null),
                new TodoElements("id3",
                        "finished todo1",
                        "content1",
                        false,
                        true,
                        LocalDateTime.now(),
                        null,
                        null),
                new TodoElements("id4",
                        "finished todo2",
                        "content2",
                        false,
                        true,
                        LocalDateTime.now(),
                        null,
                        null)
        ));

    }

    @Test
    void getTodoList() {
        when(todoRepository.getAll())
                .thenReturn(testTodoList);
        List<TodoElements> result = todo.getTodoList();
        assertThat(result).hasSize(2);
        assertThat(result.get(1).getId()).isEqualTo("id2");
    }

    @Test
    void getFinishedList() {
        when(todoRepository.getAll())
                .thenReturn(testTodoList);
        List<TodoElements> result = todo.getFinishedList();
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getId()).isEqualTo("id3");
    }

    @Test
    void createTask() {
        when(todoRepository.getAll())
                .thenReturn(testTodoList);
        int initialSize = testTodoList.size();
        TodoElements newTodo = todo.createTask("테스트", "콘텐츠");
        verify(todoRepository).create(newTodo);
        testTodoList.add(newTodo);
        assertThat(testTodoList).hasSize(initialSize + 1);

    }

    @Test
    void createFailTask() {
        List<TodoElements> existingTodos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            existingTodos.add(new TodoElements(
                    UUID.randomUUID().toString(),
                    "test title " + i,
                    "test content " + i,
                    false,
                    false,
                    LocalDateTime.now(),
                    null,
                    null
            ));
        }
        when(todoRepository.getAll()).thenReturn(existingTodos);
        assertThrows(TodoLimitExceededException.class, () -> {
            todo.createTask("새 할 일", "새 콘텐츠");
        });

    }

    @Test
    void update() {
        TodoElements originalTodo = new TodoElements(
                "id1",
                "원래 제목",
                "원래 내용",
                false,
                false,
                LocalDateTime.now(),
                null,
                null);
        when(todoRepository.getById("id1")).thenReturn(originalTodo);
        when(todoRepository.save(any(TodoElements.class))).
                thenAnswer(invocation -> invocation.getArgument(0));

        TodoElements updatedTodo =
                todo.update("id1",
                        "수정된 제목",
                        "수정된 내용");

        ArgumentCaptor<TodoElements> todoCaptor =
                ArgumentCaptor.forClass(TodoElements.class);
        verify(todoRepository).save(todoCaptor.capture());

        TodoElements savedTodo = todoCaptor.getValue();

        assertNotNull(updatedTodo);
        assertEquals("수정된 제목", savedTodo.getTitle());
        assertEquals("수정된 내용", savedTodo.getContent());
        assertEquals("id1", savedTodo.getId());

        assertEquals(savedTodo, updatedTodo);
    }

    @Test
    void taskDone() {
        TodoElements originalTodo = new TodoElements(
                "id1",
                "원래 제목",
                "원래 내용",
                false,
                false,
                LocalDateTime.now(),
                null,
                null);
        when(todoRepository.getById("id1")).thenReturn(originalTodo);
        when(todoRepository.save(any(TodoElements.class))).
                thenAnswer(invocation -> invocation.getArgument(0));

        TodoElements updatedTodo =
                todo.taskDone("id1");

        ArgumentCaptor<TodoElements> todoCaptor =
                ArgumentCaptor.forClass(TodoElements.class);
        verify(todoRepository).save(todoCaptor.capture());

        TodoElements savedTodo = todoCaptor.getValue();

        assertNotNull(updatedTodo);
        assertEquals("원래 제목", savedTodo.getTitle());
        assertEquals("원래 내용", savedTodo.getContent());
        assertEquals("id1", savedTodo.getId());
        assertTrue(savedTodo.isTaskDone());
    }

    @Test
    void delete() {
        TodoElements originalTodo = new TodoElements(
                "id1",
                "원래 제목",
                "원래 내용",
                false,
                false,
                LocalDateTime.now(),
                null,
                null);
        when(todoRepository.getById("id1")).thenReturn(originalTodo);
        when(todoRepository.save(any(TodoElements.class))).
                thenAnswer(invocation -> invocation.getArgument(0));

        TodoElements updatedTodo =
                todo.delete("id1");

        ArgumentCaptor<TodoElements> todoCaptor =
                ArgumentCaptor.forClass(TodoElements.class);
        verify(todoRepository).save(todoCaptor.capture());

        TodoElements savedTodo = todoCaptor.getValue();

        assertNotNull(updatedTodo);
        assertEquals("원래 제목", savedTodo.getTitle());
        assertEquals("원래 내용", savedTodo.getContent());
        assertEquals("id1", savedTodo.getId());
        assertTrue(savedTodo.isDeleted());
    }
}
