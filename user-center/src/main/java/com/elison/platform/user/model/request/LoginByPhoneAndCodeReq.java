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
@ApiModel("手机号验证码登录请求实体")
public class LoginByPhoneAndCodeReq implements BaseRequest {
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号长度限制为11位")
    @ApiModelProperty("手机号")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;
}
