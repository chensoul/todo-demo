package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TodoService todoService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("test");
        todo.setStatus(Todo.Status.PENDING);
        Mockito.when(todoService.findById(1L)).thenReturn(Optional.of(todo));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.title").value("test"));
    }

    @Test
    void testCreateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("test");
        todo.setStatus(Todo.Status.PENDING);
        Mockito.when(todoService.create(any(Todo.class))).thenReturn(todo);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.title").value("test"));
    }

    @Test
    void testCreateTodoWithPriority() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("test");
        todo.setStatus(Todo.Status.PENDING);
        todo.setPriority(com.example.todo.model.Priority.HIGH);
        Mockito.when(todoService.create(any(Todo.class))).thenReturn(todo);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.priority").value("HIGH"));
    }

    @Test
    void testGetTodoWithPriority() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("test");
        todo.setStatus(Todo.Status.PENDING);
        todo.setPriority(com.example.todo.model.Priority.MEDIUM);
        Mockito.when(todoService.findById(1L)).thenReturn(Optional.of(todo));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.priority").value("MEDIUM"));
    }
}
