package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel("用户名密码登录请求实体")
public class LoginByUserNameAndPasswordReq implements BaseRequest {
    @NotBlank(message = "登录用户名不能为空")
    @Length(min = 2, max = 64, message = "用户名长度限制为2-64位")
    @ApiModelProperty("登录用户名")
    private String userName;

    @NotBlank(message = "登录密码不能为空")
    @ApiModelProperty("登录密码")
    private String password;
}
