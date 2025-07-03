package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoDTO;
import com.example.todo.repository.TodoRepository;
import com.example.todo.util.TodoConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private com.example.todo.service.impl.TodoServiceImpl todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Todo todo = new Todo();
        todo.setTitle("test");
        todo.setStatus(Todo.Status.PENDING);
        todo.setGroup("工作");
        todo.setDeadline(java.time.LocalDateTime.of(2025, 7, 31, 23, 59));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        TodoDTO dto = new TodoDTO();
        dto.setTitle("test");
        dto.setStatus(Todo.Status.PENDING);
        dto.setGroup("工作");
        dto.setDeadline(java.time.LocalDateTime.of(2025, 7, 31, 23, 59));
        TodoDTO result = todoService.create(dto);
        Assertions.assertEquals("test", result.getTitle());
        Assertions.assertEquals(java.time.LocalDateTime.of(2025, 7, 31, 23, 59), result.getDeadline());
        Assertions.assertEquals("工作", result.getGroup());
    }

    @Test
    void testFindById() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDeleted(false);
        todo.setGroup("工作");
        todo.setDeadline(java.time.LocalDateTime.of(2025, 7, 31, 23, 59));
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        Optional<TodoDTO> result = todoService.findById(1L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(java.time.LocalDateTime.of(2025, 7, 31, 23, 59), result.get().getDeadline());
        Assertions.assertEquals("工作", result.get().getGroup());
    }

    @Test
    void testSearch() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Todo> page = new PageImpl<>(Collections.emptyList());
        when(todoRepository.search(anyString(), any(), isNull(), eq(pageable))).thenReturn(page);
        Page<TodoDTO> result = todoService.search("", null, null, pageable);
        Assertions.assertEquals(0, result.getTotalElements());
    }

    @Test
    void testCreateWithPriority() {
        Todo todo = new Todo();
        todo.setTitle("test");
        todo.setStatus(Todo.Status.PENDING);
        todo.setPriority(com.example.todo.model.Priority.HIGH);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        TodoDTO dto = new TodoDTO();
        dto.setTitle("test");
        dto.setStatus(Todo.Status.PENDING);
        dto.setPriority(com.example.todo.model.Priority.HIGH);
        TodoDTO result = todoService.create(dto);
        Assertions.assertEquals(com.example.todo.model.Priority.HIGH, result.getPriority());
    }

    @Test
    void testCreateDefaultPriority() {
        Todo todo = new Todo();
        todo.setTitle("test");
        todo.setStatus(Todo.Status.PENDING);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        TodoDTO dto = new TodoDTO();
        dto.setTitle("test");
        dto.setStatus(Todo.Status.PENDING);
        TodoDTO result = todoService.create(dto);
        Assertions.assertEquals(com.example.todo.model.Priority.LOW, result.getPriority());
    }

    @Test
    void testSearchByPriority() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Todo> page = new PageImpl<>(Collections.emptyList());
        when(todoRepository.search(anyString(), any(), eq(com.example.todo.model.Priority.HIGH), eq(pageable))).thenReturn(page);
        Page<TodoDTO> result = todoService.search("", null, com.example.todo.model.Priority.HIGH, pageable);
        Assertions.assertEquals(0, result.getTotalElements());
    }
}
