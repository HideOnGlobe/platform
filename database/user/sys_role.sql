DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint(20)  NOT NULL COMMENT '父角色(sys_role.id)',
--  `code` varchar(64) NOT NULL COMMENT '角色代码',
    `name`        varchar(64) NOT NULL COMMENT '角色名称',
    `role_status` smallint(6) NOT NULL DEFAULT 0 COMMENT '状态 0：待审核 1：正常 2:禁用 3:删除',
    `create_time` datetime    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   bigint(20)  NULL COMMENT '创建人',
    `update_time` datetime    NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_by`   bigint(20)  NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
#   UNIQUE KEY `sys_role_unique_key1` (`code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

BEGIN;
INSERT INTO `sys_role` (`id`, `parent_id`, `name`, `role_status`)
VALUES ('1', '0', '超级管理员', '1'),
       ('10', '1', '普通用户', '1'),
       ('20', '1', '平台管理员', '1');
COMMIT;