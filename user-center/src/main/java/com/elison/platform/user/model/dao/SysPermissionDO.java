package com.elison.platform.user.model.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.elison.platform.commons.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.model.dao
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/4 0:20
 * @UpdateDate: 2020/9/4 0:20
 **/
@Data
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel("系统权限")
@EqualsAndHashCode(callSuper = true)
public class SysPermissionDO extends BaseDO {
    private static final long serialVersionUID = -390983678783766254L;

    @ApiModelProperty("权限")
    private String permission;

    @ApiModelProperty("权限名称")
    private String name;
}
