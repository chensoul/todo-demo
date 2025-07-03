---
文件名：20250703_3_group-entity.md
创建于：2025-07-03 17:09:44
创建者：chensoul
主分支：main
任务分支：feature/20250703_3_group-entity
Yolo模式：Off
---

# 项目概览

将 Todo 分组功能升级为独立分组实体，支持分组的单独维护和多对多关联。

# 任务描述

- 分组（Group）为独立实体，分组名称唯一。
- Todo 与分组为多对多关系。
- 支持分组的增删改查（CRUD）接口。
- 删除历史 groupName 字段及相关逻辑。
- 兼容现有 Todo 业务，所有分组相关操作均通过分组实体实现。

# 分析

- 需新增分组表（group），唯一索引 name 字段。
- 需新增 Todo-Group 关联表（todo_group），实现多对多。
- Todo 实体、DTO、数据库、互转、接口、测试等需同步适配。
- 需实现分组的 CRUD Controller、Service、Repository。
- 需删除 Todo 及 DTO 的 groupName 字段及相关测试。
- 需补充/调整接口文档。
- 需回归测试所有分组相关功能。

# 提议的解决方案

- 采用 JPA 多对多映射，Group 实体与 Todo 实体互相关联。
- Group 表仅包含 id、name（唯一）、可选描述等字段。
- Todo 通过 Set<Group> 维护分组集合。
- 提供分组的标准 CRUD REST 接口。
- 删除所有 groupName 字段及相关代码。
- 补充/调整所有相关测试。

# 当前执行步骤

> [1、数据库建模与实体定义]

# 任务进度

# 最终审查

待全部开发与测试完成后补充。
