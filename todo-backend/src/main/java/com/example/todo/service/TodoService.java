package com.example.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.todo.model.Todo;
import com.example.todo.model.TodoDTO;

import java.util.Optional;

public interface TodoService {
    TodoDTO create(TodoDTO todo);
    TodoDTO update(Long id, TodoDTO todo);
    void delete(Long id);
    Optional<TodoDTO> findById(Long id);
    Page<TodoDTO> search(String keyword, Todo.Status status, com.example.todo.model.Priority priority, Pageable pageable);

    /**
     * 批量将所有未完成且已过期的事项状态置为“已过期”
     * @return 过期的事项数量
     */
    int expireOverdueTodos();
}
