package com.elison.platform.commons.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.model
 * @Description: Service入参/出参抽象类,经过Service层的对象只有DTO
 * @Author: elison
 * @CreateDate: 2020/9/3 15:09
 * @UpdateDate: 2020/9/3 15:09
 **/
@Data
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = -1002131529043377871L;

    private Long id;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    public <T extends BaseDO> T convertToDO(Class<T> tClass) {
        return BeanUtil.toBean(this, tClass);
    }

    public <T extends BaseResponse> T convertToResponse(Class<T> tClass, String... ignoreProperties) {
        return BeanUtil.toBean(this, tClass, CopyOptions.create().setIgnoreProperties(ignoreProperties));
    }

}
