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
@ApiModel("修改密码请求实体")
public class UpdatePasswordReq implements BaseRequest {

    @NotBlank(message = "登录密码不能为空")
    @ApiModelProperty("登录密码")
    private String newPassword;

    @NotBlank(message = "登录密码校验不能为空")
    @ApiModelProperty("登录密码")
    private String newPasswordAgain;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("修改密码验证码")
    private String passwordCode;

    @Length(min = 11, max = 11, message = "手机号长度限制为11位")
    @ApiModelProperty("手机号(如果已经登陆，可以不填写)")
    private String phone;
}