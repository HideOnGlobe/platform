package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@Accessors(chain = true)
@ApiModel("修改个人信息请求实体")
public class UpdateSelfUserInfoReq implements BaseRequest {

    @ApiModelProperty("登录密码")
    private String newPassword;

    @ApiModelProperty("登录密码")
    private String newPasswordAgain;

    @Length(min = 1, max = 64, message = "昵称长度限制为1-64位")
    @ApiModelProperty("昵称")
    private String nickName;

    @Length(max = 1024)
    @ApiModelProperty("头像URL")
    private String headImgUrl;

    @Length(min = 11, max = 11, message = "手机号长度限制为11位")
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("修改手机号验证码")
    private String phoneCode;

    @Email(message = "邮箱格式需要符合规范")
    @ApiModelProperty("邮箱")
    private String email;
}