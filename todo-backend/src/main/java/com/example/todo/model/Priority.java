package com.example.todo.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 任务优先级枚举
 * 支持 HIGH、MEDIUM、LOW，默认 LOW
 * 可用于国际化展示
 */
@Schema(description = "任务优先级，HIGH=高，MEDIUM=中，LOW=低，默认 LOW")
public enum Priority {
    HIGH,
    MEDIUM,
    LOW
} 