package com.elison.platform.user.enums;

import cn.hutool.core.util.ObjectUtil;
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
public enum LoginEnum {
    /**
     * 密码登录
     */
    PASSWORD(0, "密码登录"),
    /**
     * 免密登录
     */
    NO_PASSWORD(1, "免密登录"),
    ;

    private final int code;

    private final String description;

    @JsonValue
    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static LoginEnum getByCode(Integer code) {
        if (code != null) {
            for (LoginEnum statusEnum : LoginEnum.values()) {
                if (ObjectUtil.equal(code, statusEnum.getCode())) {
                    return statusEnum;
                }
            }
        }
        return null;
    }

}
