-- 待办事项分组字段变更脚本
ALTER TABLE todo ADD COLUMN `group_name` VARCHAR(50) NULL COMMENT '分组名';
