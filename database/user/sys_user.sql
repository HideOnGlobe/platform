DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_name`    varchar(64)  NOT NULL COMMENT '登陆名',
    `password`     varchar(128) NOT NULL COMMENT '密码',
    `salt`         varchar(64)  NOT NULL COMMENT '密码盐',
    `nick_name`    varchar(50)           DEFAULT NULL COMMENT '用户名',
    `email`        varchar(100)          DEFAULT NULL COMMENT '邮箱',
    `phone`        varchar(11)           DEFAULT NULL COMMENT '手机',
    `head_img_url` varchar(1024)         DEFAULT NULL COMMENT '头像url',
    `ip`           varchar(32)           DEFAULT NULL COMMENT '最终登录IP地址',
    `user_status`  smallint(6)  NOT NULL DEFAULT 0 COMMENT '状态 0：未激活 1：正常 2:禁用 3:删除',
    `create_time`  datetime     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)   NULL COMMENT '创建人',
    `update_time`  datetime     NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_by`    bigint(20)   NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_unique_key1` (`user_name`) USING BTREE,
    UNIQUE KEY `sys_user_unique_key2` (`phone`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';

BEGIN;
INSERT INTO `sys_user` (`id`, `user_name`, `password`, `salt`, `nick_name`, `phone`, `user_status`)
VALUES ('1', 'root', '3k/YRsvVocjC07574FjA4Iyl6eR2AQEkn4VAszZt/D8=', 'x3h3ycso9q5mo5fp', 'root',
        '11111111111', '1');
COMMIT;