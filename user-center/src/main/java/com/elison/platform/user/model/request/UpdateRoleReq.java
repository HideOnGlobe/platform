package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Accessors(chain = true)
@ApiModel("修改角色请求实体")
public class UpdateRoleReq implements BaseRequest {

    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("权限集合 null-不修改 集合-修改")
    private Set<Long> permissionSet;
}
