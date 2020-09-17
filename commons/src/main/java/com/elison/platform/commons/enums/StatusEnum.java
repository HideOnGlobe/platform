package com.elison.platform.commons.enums;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.enums
 * @Description: 全局状态枚举
 * @Author: elison
 * @CreateDate: 2020/9/4 12:51
 * @UpdateDate: 2020/9/4 12:51
 **/
@AllArgsConstructor
public enum StatusEnum implements IEnum {
    /**
     * 正常状态,初始化状态
     */
    NORMAL(0, "正常"),
    /**
     * 逻辑删除
     */
    DELETED(1, "已删除"),
    /**
     * 业务冻结
     */
    FREEZE(2, "已冻结"),
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

    public final StatusEnum getByCode(int code) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (ObjectUtil.equal(code, statusEnum.getCode())) {
                return statusEnum;
            }
        }
        return null;
    }


    @Override
    public Serializable getValue() {
        return this.getDescription();
    }

}
