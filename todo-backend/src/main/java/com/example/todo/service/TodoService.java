package com.example.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.todo.model.Todo;

import java.util.Optional;

public interface TodoService {
    Todo create(Todo todo);
    Todo update(Long id, Todo todo);
    void delete(Long id);
    Optional<Todo> findById(Long id);
    Page<Todo> search(String keyword, Todo.Status status, com.example.todo.model.Priority priority, Pageable pageable);
}
