package com.example.demo.presentation;

import com.example.demo.application.Todo;
import com.example.demo.infrastructure.TodoElements;
import com.example.demo.infrastructure.TodoRepository;
import com.example.demo.presentation.dto.TodoUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Todo todo;

    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    private WebApplicationContext ctx;

    private List<TodoElements> todoElementsList;
    private TodoElements todoElements;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter(
                        "UTF-8", true))
                .alwaysDo(print())
                .build();
        todoElementsList = List.of(
                new TodoElements(
                        "id1",
                        "원래 제목",
                        "원래 내용",
                        false,
                        false,
                        LocalDateTime.now(),
                        null,
                        null),
                new TodoElements(
                        "id2",
                        "원래 제목2",
                        "원래 내용2",
                        false,
                        false,
                        LocalDateTime.now(),
                        null,
                        null));
        todoElements = new TodoElements(
                "id1",
                "테스트 제목",
                "테스트 내용",
                false,
                false,
                LocalDateTime.now(),
                null,
                null);
    }

    @Test
    void todoList() throws Exception {
        when(todo.getTodoList()).thenReturn(todoElementsList);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.todoList[0].title")
                        .value("원래 제목"))
                .andExpect(jsonPath("$.todoList[0].content")
                        .value("원래 내용"));

        verify(todo).getTodoList();

    }

    @Test
    void finishedList() throws Exception {
        when(todo.getFinishedList()).thenReturn(todoElementsList);

        mockMvc.perform(get("/tasks")
                        .param("completed","true"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.todoList[0].title")
                        .value("원래 제목"))
                .andExpect(jsonPath("$.todoList[0].content")
                        .value("원래 내용"));

        verify(todo).getFinishedList();
    }

    @Test
    void create() throws Exception {
        when(todo.createTask("테스트 제목", "테스트 내용"))
                .thenReturn(todoElements);
        String json = """
                {
                    "title" : "테스트 제목",
                    "content" : "테스트 내용"
                }
                """;
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("테스트 제목")));

    }

    @Test
    void update() throws Exception {
        String todoId = "id1";
        String updatedTitle = "업데이트된 제목";
        String updatedContent = "업데이트된 내용";

        TodoUpdateRequestDto todoUpdateRequestDto =
                new TodoUpdateRequestDto(todoId, updatedTitle, updatedContent);

        TodoElements updatedTodoElements = new TodoElements(
                todoId,
                updatedTitle,
                updatedContent,
                false,
                false,
                LocalDateTime.now(),
                null,
                null
        );

        when(todo.update(todoId, updatedTitle, updatedContent))
                .thenReturn(updatedTodoElements);


        mockMvc.perform(put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "%s",
                                    "title": "%s",
                                    "content": "%s"
                                }
                                """.formatted(todoId, updatedTitle, updatedContent)))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(todoId))
                .andExpect(jsonPath("$.title").value(updatedTitle))
                .andExpect(jsonPath("$.content").value(updatedContent));

        Mockito.verify(todo).update(todoId, updatedTitle, updatedContent);

    }

    @Test
    void taskDone() throws Exception {

        String taskId = "id1";
        TodoElements completedTodoElement = new TodoElements(
                taskId,
                "완료된 제목",
                "완료된 내용",
                false,
                true,
                LocalDateTime.now(),
                null,
                LocalDateTime.now()
        );

        when(todo.taskDone(taskId)).thenReturn(completedTodoElement);

        mockMvc.perform(patch("/tasks/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("완료된 제목"))
                .andExpect(jsonPath("$.content").value("완료된 내용"))
                .andExpect(jsonPath("$.taskDone").value(true));

        Mockito.verify(todo).taskDone(taskId);
    }


    @Test
    void delete() throws Exception {
        String taskId = "id1";
        TodoElements deletedTodoElement = new TodoElements(
                taskId,
                "삭제된 제목",
                "삭제된 내용",
                true,
                false,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        when(todo.delete(taskId)).thenReturn(deletedTodoElement);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("삭제된 제목"))
                .andExpect(jsonPath("$.content").value("삭제된 내용"))
                .andExpect(jsonPath("$.isDeleted").value(true));

        Mockito.verify(todo).delete(taskId);
    }
}
