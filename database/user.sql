-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '角色代码',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `version` int(11) DEFAULT 0 COMMENT '乐观锁版本号',
  `status` smallint(6) DEFAULT 0 NOT NULL COMMENT '状态 0：正常 1：删除 2:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(11) NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(11) NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY 'code' ('code') USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '1001', '超级管理员', '2018-11-12 15:59:43', '2018-11-12 15:59:47'),
 ('2', '1002', '普通管理员', '2018-11-12 16:00:17', '2018-11-12 16:00:19');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL COMMENT '登陆名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(64) NOT NULL COMMENT '盐',
  `nick_name` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `head_img_url` varchar(1024) DEFAULT NULL COMMENT '头像url',
  `ip` varchar(32) DEFAULT NULL COMMENT '最终登录IP地址',
  `version` int(11) DEFAULT 0 COMMENT '乐观锁版本号',
  `status` smallint(6) DEFAULT 0 NOT NULL COMMENT '状态 0：正常 1：删除 2:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(11) NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(11) NULL COMMENT '修改人',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_name` (`user_name`) USING BTREE,
  UNIQUE KEY `nick_name` (`nick_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
-- INSERT INTO `sys_user` VALUES ('1', 'Crown', 'caratacus@qq.com', '13712345678', '0', '1', '2018-11-05 17:19:05', '2018-12-13 15:04:04', 'crown', '$apr1$crown$WQ2TEXVPUJ8l6N6gm0CGv.', '0:0:0:0:0:0:0:1'), ('18', 'crown1', '11@qq.com', '13718867899', '0', '1', '2018-11-19 18:56:19', '2018-12-12 15:28:18', 'crown1', '$apr1$crown1$NsepppGmlSjqtwPTlaLb1/', '0:0:0:0:0:0:0:1'), ('19', 'crown2', '13878929833@163.com', '13878929833', '1', '18', '2018-12-10 15:25:57', '2018-12-10 15:25:57', 'crown2', '$apr1$crown2$R/8LgZ.REDrXB3jlpyglI0', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `version` int(11) DEFAULT 0 COMMENT '乐观锁版本号',
  `status` smallint(6) DEFAULT 0 NOT NULL COMMENT '状态 0：正常 1：删除 2:禁用',
  `create_by` bigint(11) NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(11) NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色关系表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
-- INSERT INTO `sys_user_role` VALUES ('24', '18', '2'), ('49', '1', '1'), ('50', '19', '2');
COMMIT;

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(50) NOT NULL COMMENT '权限',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `version` int(11) DEFAULT 0 COMMENT '乐观锁版本号',
  `status` smallint(6) DEFAULT 0 NOT NULL COMMENT '状态 0：正常 1：删除 2:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(11) NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(11) NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
-- INSERT INTO `sys_user` VALUES ('1', 'Crown', 'caratacus@qq.com', '13712345678', '0', '1', '2018-11-05 17:19:05', '2018-12-13 15:04:04', 'crown', '$apr1$crown$WQ2TEXVPUJ8l6N6gm0CGv.', '0:0:0:0:0:0:0:1'), ('18', 'crown1', '11@qq.com', '13718867899', '0', '1', '2018-11-19 18:56:19', '2018-12-12 15:28:18', 'crown1', '$apr1$crown1$NsepppGmlSjqtwPTlaLb1/', '0:0:0:0:0:0:0:1'), ('19', 'crown2', '13878929833@163.com', '13878929833', '1', '18', '2018-12-10 15:25:57', '2018-12-10 15:25:57', 'crown2', '$apr1$crown2$R/8LgZ.REDrXB3jlpyglI0', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限ID',
  `version` int(11) DEFAULT 0 COMMENT '乐观锁版本号',
  `status` smallint(6) DEFAULT 0 NOT NULL COMMENT '状态 0：正常 1：删除 2:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(11) NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(11) NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色权限关系表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
-- INSERT INTO `sys_user_role` VALUES ('24', '18', '2'), ('49', '1', '1'), ('50', '19', '2');
COMMIT;