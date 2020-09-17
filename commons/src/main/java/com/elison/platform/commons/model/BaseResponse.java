package com.elison.platform.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.model
 * @Description: 显示层对象抽象类,Controller出参对象
 * @Author: elison
 * @CreateDate: 2020/9/3 15:10
 * @UpdateDate: 2020/9/3 15:10
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseResponse implements Serializable {
    private static final long serialVersionUID = -3452273092881679202L;
}
