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
@TableName("sys_role")
@ApiModel("系统角色")
@EqualsAndHashCode(callSuper = true)
public class SysRoleDO extends BaseDO {
    private static final long serialVersionUID = -1765822356075137596L;

    @ApiModelProperty("角色代码")
    private String code;

    @ApiModelProperty("角色名称")
    private String name;
}
