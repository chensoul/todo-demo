package com.example.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.todo.model.Todo;
import com.example.todo.model.Priority;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("SELECT t FROM Todo t WHERE t.deleted = false AND (:keyword IS NULL OR t.title LIKE %:keyword% OR t.description LIKE %:keyword%) AND (:status IS NULL OR t.status = :status) AND (:priority IS NULL OR t.priority = :priority)")
    Page<Todo> search(@Param("keyword") String keyword, @Param("status") Todo.Status status, @Param("priority") com.example.todo.model.Priority priority, Pageable pageable);
}
