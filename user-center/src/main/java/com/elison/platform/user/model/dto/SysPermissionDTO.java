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
@ApiModel("系统权限")
@EqualsAndHashCode(callSuper = true)
public class SysPermissionDTO extends BaseDTO {
    private static final long serialVersionUID = 3667528154506399318L;

    @ApiModelProperty("权限")
    private String permission;

    @ApiModelProperty("权限名称")
    private String name;
}
