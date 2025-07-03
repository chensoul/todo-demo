package com.example.todo.service.impl;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoDTO;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import com.example.todo.util.TodoConverter;
import com.example.todo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Exception.class)
public class TodoServiceImpl implements TodoService {
    private static final Logger log = LoggerFactory.getLogger(TodoServiceImpl.class);
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public int expireOverdueTodos() {
        List<Todo.Status> toExpireStatuses = Arrays.asList(Todo.Status.PENDING, Todo.Status.IN_PROGRESS);
        List<Todo> overdueTodos = todoRepository.findAllToExpire(toExpireStatuses, LocalDateTime.now());
        int count = 0;
        for (Todo todo : overdueTodos) {
            if (todo.getStatus() != Todo.Status.EXPIRED) {
                todo.setStatus(Todo.Status.EXPIRED);
                todoRepository.save(todo);
                count++;
                log.info("Todo id {} expired by scheduler", todo.getId());
            }
        }
        return count;
    }

    @Override
    public TodoDTO create(TodoDTO todoDTO) {
        log.info("Creating todo: {}", todoDTO.getTitle());
        Todo todo = TodoConverter.toEntity(todoDTO);
        todo.setId(null);
        todo.setDeleted(false);
        Todo saved = todoRepository.save(todo);
        return TodoConverter.toDTO(saved);
    }

    @Override
    public TodoDTO update(Long id, TodoDTO todoDTO) {
        log.info("Updating todo id: {}", id);
        Optional<Todo> optional = todoRepository.findById(id);
        if (optional.isEmpty() || Boolean.TRUE.equals(optional.get().getDeleted())) {
            log.warn("Todo not found for update, id: {}", id);
            throw new BusinessException("todo.notfound");
        }
        Todo old = optional.get();
        old.setTitle(todoDTO.getTitle());
        old.setDescription(todoDTO.getDescription());
        old.setStatus(todoDTO.getStatus());
        old.setPriority(todoDTO.getPriority());
        Todo saved = todoRepository.save(old);
        return TodoConverter.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting todo id: {}", id);
        Optional<Todo> optional = todoRepository.findById(id);
        if (optional.isEmpty() || Boolean.TRUE.equals(optional.get().getDeleted())) {
            log.warn("Todo not found for delete, id: {}", id);
            throw new BusinessException("todo.notfound");
        }
        Todo todo = optional.get();
        todo.setDeleted(true);
        todoRepository.save(todo);
    }

    @Override
    public Optional<TodoDTO> findById(Long id) {
        Optional<Todo> optional = todoRepository.findById(id);
        if (optional.isEmpty() || Boolean.TRUE.equals(optional.get().getDeleted())) {
            return Optional.empty();
        }
        return Optional.of(TodoConverter.toDTO(optional.get()));
    }

    @Override
    public Page<TodoDTO> search(String keyword, Todo.Status status, com.example.todo.model.Priority priority, Pageable pageable) {
        Page<Todo> page = todoRepository.search(keyword, status, priority, pageable);
        return page.map(TodoConverter::toDTO);
    }
}
