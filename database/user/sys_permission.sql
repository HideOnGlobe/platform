DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `permission`  varchar(50) NOT NULL COMMENT '权限',
    `name`        varchar(50) NOT NULL COMMENT '权限名称',
    `create_time` datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   bigint(20)  NULL COMMENT '创建人',
    `update_time` datetime    NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_by`   bigint(20)  NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_permission_unique_key1` (`permission`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统权限表';

BEGIN;
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (1, 'ADMIN', '管理员权限');
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (2, 'GET_ADMIN_USER', '查看管理用户权限');
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (3, 'INSERT_ADMIN_USER', '新增管理用户权限');
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (4, 'UPDATE_ADMIN_USER', '修改管理用户权限');
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (5, 'DELETE_ADMIN_USER', '删除管理用户权限');
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (6, 'INSERT_ROLE', '新增角色权限');
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (7, 'UPDATE_ROLE', '修改角色权限');
INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (8, 'DELETE_ROLE', '删除角色权限');
COMMIT;
-- ^map.*\(
-- INSERT INTO `sys_permission` (`id`, `permission`, `name`) VALUES (1,'