DROP TABLE IF EXISTS `sys_user_wx`;
CREATE TABLE `sys_user_wx`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_id`      bigint(20)   NOT NULL COMMENT '用户(sys_user.id)',
    `open_id`      varchar(128) NOT NULL COMMENT 'wx_open_id',
    `union_id`     varchar(64)  NOT NULL COMMENT 'wx_union_id',
    `create_time`  datetime     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)   NULL COMMENT '创建人',
    `update_time`  datetime     NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_by`    bigint(20)   NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_wx_unique_key1` (`user_id`) USING BTREE,
    UNIQUE KEY `sys_user_wx_unique_key2` (`open_id`) USING BTREE,
    UNIQUE KEY `sys_user_wx_unique_key3` (`union_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';