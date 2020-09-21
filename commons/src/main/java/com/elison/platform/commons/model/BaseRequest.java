package com.elison.platform.commons.model;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.model
 * @Description: 业务对象抽象类,Controller入参
 * @Author: elison
 * @CreateDate: 2020/9/3 15:09
 * @UpdateDate: 2020/9/3 15:09
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRequest implements Serializable {
    private static final long serialVersionUID = 8936598111078827315L;

    public <T extends BaseDTO> T convert(Class<T> tClass) {
        return BeanUtil.toBean(this, tClass);
    }

}
