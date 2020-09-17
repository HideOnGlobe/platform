package com.elison.platform.user.model.dto;

import com.elison.platform.commons.model.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.model.dto
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 11:07
 * @UpdateDate: 2020/9/9 11:07
 **/
@Data
@Accessors(chain = true)
@ApiModel("系统角色")
@EqualsAndHashCode(callSuper = true)
public class SysRoleDTO extends BaseDTO {
    private static final long serialVersionUID = 476546540849597051L;

    @ApiModelProperty("角色代码")
    private String code;

    @ApiModelProperty("角色名称")
    private String name;
}
