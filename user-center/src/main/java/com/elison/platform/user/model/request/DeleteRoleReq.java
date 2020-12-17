package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Accessors(chain = true)
@ApiModel("删除角色请求实体")
public class DeleteRoleReq implements BaseRequest {

    @NotEmpty(message = "角色ID集合不能为空")
    @ApiModelProperty("角色ID集合")
    private Set<Long> roleIdSet;

}
