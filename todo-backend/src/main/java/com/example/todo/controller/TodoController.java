package com.example.todo.controller;

import com.example.todo.model.ApiResponse;
import com.example.todo.model.Todo;
import com.example.todo.model.TodoDTO;
import com.example.todo.service.TodoService;
import com.example.todo.util.TodoConverter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private static final Logger log = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;
    private final MessageSource messageSource;

    public TodoController(TodoService todoService, MessageSource messageSource) {
        this.todoService = todoService;
        this.messageSource = messageSource;
    }

    @PostMapping
    /**
     * 新建待办事项，支持可选分组 group 字段
     */
    public ApiResponse<TodoDTO> create(@Valid @RequestBody TodoDTO todoDTO, Locale locale) {
        log.info("API create todo: {}", todoDTO.getTitle());
        TodoDTO created = todoService.create(todoDTO);
        String msg = messageSource.getMessage("todo.create.success", null, locale);
        return ApiResponse.success(msg, created);
    }

    @PutMapping("/{id}")
    /**
     * 更新待办事项，支持可选分组 group 字段
     */
    public ApiResponse<TodoDTO> update(@PathVariable Long id, @Valid @RequestBody TodoDTO todoDTO, Locale locale) {
        log.info("API update todo id: {}", id);
        TodoDTO updated = todoService.update(id, todoDTO);
        String msg = messageSource.getMessage("todo.update.success", null, locale);
        return ApiResponse.success(msg, updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, Locale locale) {
        log.info("API delete todo id: {}", id);
        todoService.delete(id);
        String msg = messageSource.getMessage("todo.delete.success", null, locale);
        return ApiResponse.success(msg, null);
    }

    @GetMapping("/{id}")
    public ApiResponse<TodoDTO> get(@PathVariable Long id, Locale locale) {
        log.info("API get todo id: {}", id);
        Optional<TodoDTO> todo = todoService.findById(id);
        if (todo.isEmpty()) {
            String msg = messageSource.getMessage("todo.notfound", null, locale);
            return ApiResponse.error(404, msg);
        }
        String msg = messageSource.getMessage("todo.get.success", null, locale);
        return ApiResponse.success(msg, todo.get());
    }

    @GetMapping
    public ApiResponse<Page<TodoDTO>> list(
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
        Page<TodoDTO> result = todoService.search(keyword, status, priority, pageable);
        String msg = messageSource.getMessage("todo.get.success", null, locale);
        return ApiResponse.success(msg, result);
    }
}
