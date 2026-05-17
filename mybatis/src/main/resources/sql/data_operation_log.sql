-- 数据操作日志表
-- 记录方法的执行信息，包括操作人、执行时间、类名、方法名、参数、返回值、执行时长
CREATE TABLE IF NOT EXISTS data_operation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    operator VARCHAR(100) NOT NULL COMMENT '操作人',
    operation_time DATETIME NOT NULL COMMENT '操作时间',
    class_name VARCHAR(500) NOT NULL COMMENT '执行方法的全类名',
    method_name VARCHAR(200) NOT NULL COMMENT '执行方法名',
    params TEXT COMMENT '方法运行时参数(JSON格式)',
    return_value TEXT COMMENT '返回值(JSON格式)',
    duration BIGINT NOT NULL COMMENT '方法执行时长(毫秒)',
    INDEX idx_operation_time (operation_time),
    INDEX idx_class_name (class_name(100)),
    INDEX idx_operator (operator)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据操作日志表';
