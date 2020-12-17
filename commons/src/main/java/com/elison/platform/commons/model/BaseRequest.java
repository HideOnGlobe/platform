package com.elison.platform.commons.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.model
 * @Description: 业务对象抽象类, Controller入参
 * @Author: elison
 * @CreateDate: 2020/9/3 15:09
 * @UpdateDate: 2020/9/3 15:09
 **/
@Accessors(chain = true)
public interface BaseRequest extends Serializable {

    /**
     * 枚举请在此处添加属性名，并手动进行赋值
     */
    default <T extends BaseDTO> T convertToDTO(Class<T> tClass, String... ignoreProperties) {
        return BeanUtil.toBean(this, tClass, CopyOptions.create().setIgnoreProperties(ignoreProperties));
    }

}
