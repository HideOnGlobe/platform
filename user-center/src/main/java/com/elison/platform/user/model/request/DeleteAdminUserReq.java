package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("删除管理用户请求实体")
public class DeleteAdminUserReq implements BaseRequest {
    @NotEmpty(message = "用户Id列表不能为空")
    @ApiModelProperty("用户Id列表")
    private Set<Long> userIdSet;
}
