package com.elison.platform.user.enums;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.enums
 * @Description: 用户状态枚举
 * @Author: elison
 * @CreateDate: 2020/9/4 12:51
 * @UpdateDate: 2020/9/4 12:51
 **/
@AllArgsConstructor
public enum RoleStatusEnum {
    /**
     * 未激活状态,初始化状态
     */
    NOT_ACTIVE(0, "未激活"),
    /**
     * 正常状态
     */
    NORMAL(1, "正常"),
    /**
     * 业务冻结
     */
    FREEZE(2, "禁用"),
    /**
     * 逻辑删除
     */
    DELETED(3, "删除"),
    ;

    @EnumValue
    private final int code;

    private final String description;

    @JsonValue
    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static RoleStatusEnum getByCode(Integer code) {
        if (code != null) {
            for (RoleStatusEnum statusEnum : RoleStatusEnum.values()) {
                if (ObjectUtil.equal(code, statusEnum.getCode())) {
                    return statusEnum;
                }
            }
        }
        return null;
    }

}
