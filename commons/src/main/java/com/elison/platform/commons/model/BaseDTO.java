package com.elison.platform.commons.model;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.model
 * @Description: Service入参/出参抽象类,经过Service层的对象只有DTO
 * @Author: elison
 * @CreateDate: 2020/9/3 15:09
 * @UpdateDate: 2020/9/3 15:09
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = -1002131529043377871L;

    private Long id;

    public <T extends BaseDO> T convertToDO(Class<T> tClass) {
        return BeanUtil.toBean(this, tClass);
    }

    public <T extends BaseResponse> T convertToResponse(Class<T> tClass) {
        return BeanUtil.toBean(this, tClass);
    }

}
