package com.example.todo.util;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoDTO;

public class TodoConverter {
    public static TodoDTO toDTO(Todo todo) {
        if (todo == null) return null;
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setStatus(todo.getStatus());
        dto.setPriority(todo.getPriority());
        dto.setDeleted(todo.getDeleted());
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setUpdatedAt(todo.getUpdatedAt());
        dto.setDeadline(todo.getDeadline());
        return dto;
    }

    public static Todo toEntity(TodoDTO dto) {
        if (dto == null) return null;
        Todo todo = new Todo();
        todo.setId(dto.getId());
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setStatus(dto.getStatus());
        todo.setPriority(dto.getPriority());
        todo.setDeleted(dto.getDeleted());
        todo.setCreatedAt(dto.getCreatedAt());
        todo.setUpdatedAt(dto.getUpdatedAt());
        todo.setDeadline(dto.getDeadline());
        return todo;
    }
}
