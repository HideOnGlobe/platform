DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)      DEFAULT NULL COMMENT '用户(sys_user.id)',
    `role_id`     bigint(20)      DEFAULT NULL COMMENT '角色(sys_role.id)',
    `create_time` datetime   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   bigint(20) NULL COMMENT '创建人',
    `update_time` datetime   NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_by`   bigint(20) NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_role_unique_key1` (`user_id`, `role_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户角色关系表';

BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1', '1');
COMMIT;