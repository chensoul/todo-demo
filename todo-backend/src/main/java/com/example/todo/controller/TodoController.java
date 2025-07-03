package com.example.todo.controller;

import com.example.todo.model.ApiResponse;
import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public ApiResponse<Todo> create(@Valid @RequestBody Todo todo, Locale locale) {
        Todo created = todoService.create(todo);
        String msg = messageSource.getMessage("todo.create.success", null, locale);
        return ApiResponse.success(msg, created);
    }

    @PutMapping("/{id}")
    public ApiResponse<Todo> update(@PathVariable Long id, @Valid @RequestBody Todo todo, Locale locale) {
        Todo updated = todoService.update(id, todo);
        String msg = messageSource.getMessage("todo.update.success", null, locale);
        return ApiResponse.success(msg, updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, Locale locale) {
        todoService.delete(id);
        String msg = messageSource.getMessage("todo.delete.success", null, locale);
        return ApiResponse.success(msg, null);
    }

    @GetMapping("/{id}")
    public ApiResponse<Todo> get(@PathVariable Long id, Locale locale) {
        Optional<Todo> todo = todoService.findById(id);
        if (todo.isEmpty()) {
            String msg = messageSource.getMessage("todo.notfound", null, locale);
            return ApiResponse.error(404, msg);
        }
        String msg = messageSource.getMessage("todo.get.success", null, locale);
        return ApiResponse.success(msg, todo.get());
    }

    @GetMapping
    public ApiResponse<Page<Todo>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Todo.Status status,
            @RequestParam(required = false) com.example.todo.model.Priority priority,
            Locale locale) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && "asc".equalsIgnoreCase(sortParams[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        Page<Todo> result = todoService.search(keyword, status, priority, pageable);
        String msg = messageSource.getMessage("todo.get.success", null, locale);
        return ApiResponse.success(msg, result);
    }
}
