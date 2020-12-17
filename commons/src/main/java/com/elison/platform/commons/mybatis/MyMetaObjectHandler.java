package com.elison.platform.commons.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.elison.platform.commons.utils.BaseUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.mybatis
 * @Description: 自动填充功能
 * @Author: elison
 * @CreateDate: 2020/9/12 22:37
 * @UpdateDate: 2020/9/12 22:37
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", getCurrentUserId(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", getCurrentUserId(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", getCurrentUserId(), metaObject);
    }

    private static BaseUserUtil util = null;
    private static Boolean init = false;

    private Long getCurrentUserId() {
        if (!init) {
            init = true;
            try {
                util = (BaseUserUtil) Class.forName("com.elison.platform.user.shiro.UserUtil").newInstance();
            } catch (Exception e) {
                log.error("通用模块MyMetaObjectHandler获取用户ID方法初始化失败！");
            }
        }
        Long userId = null;
        try {
            userId = util.getCurrentUserId();
        } catch (Exception ignored) {
        }
        return userId;
    }
}
