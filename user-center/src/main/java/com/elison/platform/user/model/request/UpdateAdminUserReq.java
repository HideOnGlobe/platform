package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("修改管理用户请求实体")
public class UpdateAdminUserReq implements BaseRequest {
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色ID列表")
    private List<Long> roleList;

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

    @Email(message = "邮箱格式需要符合规范")
    @ApiModelProperty("邮箱")
    private String email;

    @Range(min = 1, max = 2, message = "用户状态请在允许范围内操作")
    @ApiModelProperty("用户状态 1:正常 2:禁用")
    private Integer userStatus;
}