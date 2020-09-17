package com.elison.platform.commons.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.elison.platform.commons.constants.NumConstants;
import com.elison.platform.commons.utils.BaseUserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.mybatis
 * @Description: 自动填充功能
 * @Author: elison
 * @CreateDate: 2020/9/12 22:37
 * @UpdateDate: 2020/9/12 22:37
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "version", Integer.class, NumConstants.INTEGER_ONE);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", Long.class, getCurrentUserId());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateBy", Long.class, getCurrentUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateBy", Long.class, getCurrentUserId());
    }

    private Long getCurrentUserId() {
        Long userId = null;
        try {
            userId = BaseUserUtil.getCurrentUserId();
        } catch (Exception ignored) {
        }
        return userId;
    }
}
