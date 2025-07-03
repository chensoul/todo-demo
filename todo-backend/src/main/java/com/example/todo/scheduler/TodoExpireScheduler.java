package com.example.todo.scheduler;

import com.example.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TodoExpireScheduler {
    private static final Logger log = LoggerFactory.getLogger(TodoExpireScheduler.class);
    private final TodoService todoService;

    public TodoExpireScheduler(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * 定时批量过期未完成且已过期的事项
     * 默认每5分钟执行一次，可通过配置覆盖
     */
    @Scheduled(cron = "${todo.expire.cron:0 0/5 * * * ?}")
    public void expireOverdueTodos() {
        int count = todoService.expireOverdueTodos();
        if (count > 0) {
            log.info("[定时任务] 批量过期 {} 条待办事项", count);
        }
    }
}
