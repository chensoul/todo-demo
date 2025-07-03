package com.example.todo.service.impl;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Todo create(Todo todo) {
        todo.setId(null);
        todo.setDeleted(false);
        return todoRepository.save(todo);
    }

    @Override
    public Todo update(Long id, Todo todo) {
        Optional<Todo> optional = todoRepository.findById(id);
        if (optional.isEmpty() || Boolean.TRUE.equals(optional.get().getDeleted())) {
            throw new IllegalArgumentException("todo.notfound");
        }
        Todo old = optional.get();
        old.setTitle(todo.getTitle());
        old.setDescription(todo.getDescription());
        old.setStatus(todo.getStatus());
        return todoRepository.save(old);
    }

    @Override
    public void delete(Long id) {
        Optional<Todo> optional = todoRepository.findById(id);
        if (optional.isEmpty() || Boolean.TRUE.equals(optional.get().getDeleted())) {
            throw new IllegalArgumentException("todo.notfound");
        }
        Todo todo = optional.get();
        todo.setDeleted(true);
        todoRepository.save(todo);
    }

    @Override
    public Optional<Todo> findById(Long id) {
        Optional<Todo> optional = todoRepository.findById(id);
        if (optional.isEmpty() || Boolean.TRUE.equals(optional.get().getDeleted())) {
            return Optional.empty();
        }
        return optional;
    }

    @Override
    public Page<Todo> search(String keyword, Todo.Status status, com.example.todo.model.Priority priority, Pageable pageable) {
        return todoRepository.search(keyword, status, priority, pageable);
    }
}
