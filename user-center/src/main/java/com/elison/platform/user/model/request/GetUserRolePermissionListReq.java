package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("查询角色权限列表请求实体")
public class GetUserRolePermissionListReq implements BaseRequest {
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty("用户角色ID")
    private Long roleId;
}
