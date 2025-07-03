package com.example.todo.controller;

import com.example.todo.model.TodoDTO;
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
        TodoDTO todo = new TodoDTO();
        todo.setId(1L);
        todo.setTitle("test");
        todo.setStatus(com.example.todo.model.Todo.Status.PENDING);
        todo.setGroupName("工作");
        todo.setGroupName("工作");
        todo.setDeadline(java.time.LocalDateTime.of(2025, 7, 31, 23, 59));
        Mockito.when(todoService.findById(1L)).thenReturn(Optional.of(todo));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.title").value("test"))
                .andExpect(jsonPath("$.data.deadline").value("2025-07-31T23:59:00"))
                .andExpect(jsonPath("$.data.groupName").value("工作"));
    }

    @Test
    void testCreateTodo() throws Exception {
        TodoDTO todo = new TodoDTO();
        todo.setTitle("test");
        todo.setStatus(com.example.todo.model.Todo.Status.PENDING);
        todo.setDeadline(java.time.LocalDateTime.of(2025, 7, 31, 23, 59));
        todo.setGroupName("工作");
        Mockito.when(todoService.create(any(TodoDTO.class))).thenReturn(todo);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.title").value("test"))
                .andExpect(jsonPath("$.data.deadline").value("2025-07-31T23:59:00"))
                .andExpect(jsonPath("$.data.groupName").value("工作"));
    }

    @Test
    void testCreateTodoWithPriority() throws Exception {
        TodoDTO todo = new TodoDTO();
        todo.setTitle("test");
        todo.setStatus(com.example.todo.model.Todo.Status.PENDING);
        todo.setPriority(com.example.todo.model.Priority.HIGH);
        Mockito.when(todoService.create(any(TodoDTO.class))).thenReturn(todo);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.priority").value("HIGH"));
    }

    @Test
    void testGetTodoWithPriority() throws Exception {
        TodoDTO todo = new TodoDTO();
        todo.setId(1L);
        todo.setTitle("test");
        todo.setStatus(com.example.todo.model.Todo.Status.PENDING);
        todo.setPriority(com.example.todo.model.Priority.MEDIUM);
        Mockito.when(todoService.findById(1L)).thenReturn(Optional.of(todo));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.priority").value("MEDIUM"));
    }
}
