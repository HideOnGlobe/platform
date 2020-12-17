package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("新增管理员用户请求实体")
public class RegisterAdminUserReq implements BaseRequest {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 1, max = 64, message = "用户名长度限制为1-64位")
    @ApiModelProperty("登录用户名")
    private String userName;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号长度限制为11位")
    @ApiModelProperty("手机号")
    private String phone;

    @NotEmpty(message = "角色不能为空")
    @ApiModelProperty("角色ID列表")
    private List<Long> roleList;
}
