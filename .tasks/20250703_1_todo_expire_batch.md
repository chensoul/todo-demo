---
文件名：2025-07-03_1_todo-expire-batch.md
创建于：2025-07-03_13:57:44
创建者：chensoul
主分支：main
任务分支：feature/2025-07-03_1_todo-expire-batch
Yolo模式：Off
---

# 项目概览

为待办事项（Todo）添加截止时间字段，并实现定时任务批量更新“过期”状态：若事项在截止时间前未完成，状态自动变为“已过期”。

# 任务描述

- Todo 实体和 DTO 增加 deadline 字段（LocalDateTime）。
- Todo.Status 枚举增加 EXPIRED 状态。
- 实现 Spring 定时任务，定期批量将所有未完成且已过期的事项状态置为“已过期”。
- 相关 controller/service/repository/测试同步适配。

# 分析

- 现有 Todo、TodoDTO、Status 枚举未包含 deadline/EXPIRED。
- 需修改数据库表结构，增加 deadline 字段。
- 定时任务建议用 @Scheduled，周期可配置。
- 需保证定时任务性能和幂等性。
- 需补充单元测试覆盖。

# 提议的解决方案

- 数据结构：Todo、TodoDTO、Status 枚举扩展。
- 数据库：增加 deadline 字段，支持 null。
- 定时任务：service 层新增批量过期逻辑，@Scheduled 定期执行。
- 业务逻辑：controller/service 层支持 deadline 的增删查改。
- 测试：补充过期相关测试。

# 实施清单

- [x] 1、修改 Todo 实体，新增 deadline 字段，Status 枚举增加 EXPIRED。
- [x] 2、修改 TodoDTO，新增 deadline 字段。
- [x] 3、修改数据库表结构，增加 deadline 字段（提供 SQL 变更脚本）。
- [x] 4、Repository 层增加按状态和截止时间批量查询/更新方法。
- [x] 5、Service 层暴露批量过期方法。
- [x] 6、新增定时任务类 TodoExpireScheduler，定期批量更新过期事项状态。
- [x] 7、Controller 层支持 deadline 字段的增删查改。
- [x] 8、修改/补充单元测试和集成测试，覆盖截止时间和过期状态相关逻辑。
- [x] 9、配置 application.yml，增加定时任务周期配置项。
- [x] 10、代码编译、测试、回归，确保所有功能和测试通过。

# 任务进度

2025-07-03 15:17:27
- 已执行：mvn clean test
- 更改：全量编译并运行所有单元/集成测试，全部通过
- 原因：回归验证所有功能和测试均正常
- 阻碍因素：无
- 状态：成功

2025-07-03 15:15:57
- 已修改：src/main/resources/application.yml
- 更改：新增 todo.expire.cron 配置项，支持定时任务周期灵活调整
- 原因：便于运维和调度策略灵活配置
- 阻碍因素：无
- 状态：成功

2025-07-03 15:13:56
- 已修改：src/test/java/com/example/todo/controller/TodoControllerTest.java src/test/java/com/example/todo/service/TodoServiceTest.java
- 更改：单元测试用例补充/校验 deadline 字段的收发、存取、查询，保证覆盖截止时间相关逻辑
- 原因：确保 deadline 字段端到端功能和测试覆盖
- 阻碍因素：无
- 状态：成功

2025-07-03 15:12:20
- 已修改：src/main/java/com/example/todo/util/TodoConverter.java
- 更改：DTO/实体互转时同步支持 deadline 字段，保证 Controller 层收发和查询均含截止时间
- 原因：保证前后端 deadline 字段全流程通路
- 阻碍因素：无
- 状态：成功

2025-07-03 15:07:37
- 已修改：src/main/java/com/example/todo/scheduler/TodoExpireScheduler.java
- 更改：新增定时任务类，@Scheduled(cron = "${todo.expire.cron:0 0/5 * * * ?}")，定期批量过期未完成事项
- 原因：实现自动过期功能，支持配置化调度周期
- 阻碍因素：无
- 状态：成功

2025-07-03 15:05:33
- 已修改：src/main/java/com/example/todo/service/TodoService.java src/main/java/com/example/todo/service/impl/TodoServiceImpl.java
- 更改：新增批量过期方法 expireOverdueTodos，遍历所有未完成且已过期事项并置为 EXPIRED，返回过期数量
- 原因：为定时任务批量过期提供业务实现
- 阻碍因素：无
- 状态：成功

2025-07-03 15:00:35
- 已修改：src/main/java/com/example/todo/repository/TodoRepository.java
- 更改：新增批量查询所有状态为 PENDING/IN_PROGRESS 且 deadline < 当前时间的事项的方法
- 原因：为定时任务批量过期提供数据支撑
- 阻碍因素：无
- 状态：成功


2025-07-03 14:50:25
- 已修改：src/main/java/com/example/todo/model/Todo.java
- 更改：新增 deadline 字段，Status 枚举增加 EXPIRED
- 原因：支持事项截止时间与过期状态
- 阻碍因素：无
- 状态：成功

2025-07-03 14:50:25
- 已修改：src/main/java/com/example/todo/model/TodoDTO.java
- 更改：新增 deadline 字段
- 原因：DTO 层同步支持截止时间
- 阻碍因素：无
- 状态：成功

2025-07-03 13:57:44
- 已修改：.tasks/2025-07-03_1_todo-expire-batch.md
- 更改：补建任务文件，记录需求与分析
- 原因：补全 RIPER-5 规范流程，便于后续追踪
- 阻碍因素：无
- 状态：成功

# 最终审查

待全部开发与测试完成后补充。
