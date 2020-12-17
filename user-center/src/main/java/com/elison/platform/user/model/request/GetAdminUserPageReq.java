package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import com.elison.platform.commons.page.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("查看管理用户请求实体")
@EqualsAndHashCode(callSuper = true)
public class GetAdminUserPageReq extends PageQuery implements BaseRequest {
    @ApiModelProperty("登录用户名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @Range(min = 0, max = 3, message = "用户状态请在允许范围内操作")
    @ApiModelProperty("用户状态 0:未激活 1:正常 2:禁用 3:删除")
    private Integer userStatus;
}