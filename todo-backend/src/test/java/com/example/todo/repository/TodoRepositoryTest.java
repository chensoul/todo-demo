package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testSaveAndSearch() {
        Todo todo = new Todo();
        todo.setTitle("test repo");
        todo.setStatus(Todo.Status.PENDING);
        todo.setDeleted(false);
        todoRepository.save(todo);
        Page<Todo> page = todoRepository.search("test", Todo.Status.PENDING, null, PageRequest.of(0, 10));
        Assertions.assertFalse(page.isEmpty());
        Assertions.assertEquals("test repo", page.getContent().get(0).getTitle());
    }

    @Test
    void testSaveAndSearchByPriority() {
        Todo todo = new Todo();
        todo.setTitle("test high");
        todo.setStatus(Todo.Status.PENDING);
        todo.setPriority(com.example.todo.model.Priority.HIGH);
        todo.setDeleted(false);
        todoRepository.save(todo);
        Page<Todo> page = todoRepository.search(null, null, com.example.todo.model.Priority.HIGH, PageRequest.of(0, 10));
        Assertions.assertFalse(page.isEmpty());
        Assertions.assertEquals(com.example.todo.model.Priority.HIGH, page.getContent().get(0).getPriority());
    }
}
