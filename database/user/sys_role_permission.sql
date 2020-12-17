DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id`       bigint(20)      DEFAULT NULL COMMENT '角色(sys_user.id)',
    `permission_id` bigint(20)      DEFAULT NULL COMMENT '权限(sys_permission.id)',
    `create_time`   datetime   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     bigint(20) NULL COMMENT '创建人',
    `update_time`   datetime   NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_by`     bigint(20) NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_permission_unique_key1` (`role_id`, `permission_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统角色权限关系表';

BEGIN;
-- 普通用户start
-- 普通用户end

-- 平台管理员start
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200001', '20', '1');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200002', '20', '2');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200003', '20', '3');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200004', '20', '4');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200005', '20', '5');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200006', '20', '6');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200007', '20', '7');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES ('200008', '20', '8');
-- 平台管理员end


COMMIT;