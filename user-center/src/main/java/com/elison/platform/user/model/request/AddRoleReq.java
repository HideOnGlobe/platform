package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@ApiModel("新增角色请求实体")
public class AddRoleReq implements BaseRequest {

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty("角色名称")
    private String roleName;

    @NotNull(message = "父角色ID不能为空")
    @ApiModelProperty("父角色ID")
    private Long parentId;

    @ApiModelProperty("权限集合")
    private Set<Long> permissionSet = new HashSet<>();
}
